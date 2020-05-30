package com.example.kisaanonline;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import com.example.kisaanonline.Adapters.CartListAdapter;
import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.ApiResults.CartListResult;
import com.example.kisaanonline.Fragments.AboutFragment;
import com.example.kisaanonline.Fragments.CartDetailsFragment;
import com.example.kisaanonline.Fragments.ContactUsFragment;
import com.example.kisaanonline.Fragments.HomeFragment;
import com.example.kisaanonline.Fragments.LoginFragment;
import com.example.kisaanonline.Models.AuthenticationCredentials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout cartListLayout,displayOptionsLayout;
    private MenuItem cartMenuItem, hamburgerMenuItem, profileMenuItem;
    private TextView homeOption,aboutOption,contactOption;
    private Button cartDetailsBtn;
    private RecyclerView cartListRecyclerView;
    private TextView cartTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialiseUtils();
        setListenersToUtils();

        intialiseMenuButtons();

        Utils.setFragment(MainActivity.this,new HomeFragment(),true);

    }

    private void intialiseMenuButtons(){

        //Initialising Cart Menu Button
        cartListLayout = findViewById(R.id.cart_list_layout);
        cartDetailsBtn = findViewById(R.id.cart_details_btn);
        cartListRecyclerView = findViewById(R.id.cart_recycler_view);
        cartListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartTotalPrice = findViewById(R.id.total_cart_amount);
        cartDetailsBtn.setOnClickListener(this);

        //Initialising Hamburger Menu Button
        displayOptionsLayout = findViewById(R.id.display_options_layout);
        homeOption = findViewById(R.id.home_option);
        aboutOption = findViewById(R.id.about_option);
        contactOption = findViewById(R.id.contact_option);
        homeOption.setOnClickListener(this);
        aboutOption.setOnClickListener(this);
        contactOption.setOnClickListener(this);

    }

    private void cartDetailsBtnClicked(){
            Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            Utils.HOME_OPTION_COLOR.setValue(R.color.colorOrange);
            Utils.setFragment(MainActivity.this, new HomeFragment(),true);
    }

    private void homeOptionClicked(){
            Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            Utils.HOME_OPTION_COLOR.setValue(R.color.colorOrange);
            Utils.setFragment(MainActivity.this, new HomeFragment(),true);
    }

    private void aboutOptionClicked() {
        Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
        Utils.ABOUT_OPTION_COLOR.setValue(R.color.colorOrange);
        Utils.setFragment(MainActivity.this, new AboutFragment(), true);
    }

    private void contactOptionClicked(){
            Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            Utils.CONTACT_OPTION_COLOR.setValue(R.color.colorOrange);
            Utils.setFragment(MainActivity.this, new ContactUsFragment(),true);
        }


    private void setListenersToUtils(){
        Utils.CART_LIST_VISIBILITY.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                cartListLayout.setVisibility(visibility);
                switch(visibility){
                    case View.VISIBLE:
                        cartMenuItem.setChecked(true);
                        Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
                    case View.INVISIBLE:
                        cartMenuItem.setChecked(false);
                    case View.GONE:
                        cartMenuItem.setChecked(false);
                    default:
                        cartMenuItem.setChecked(false);
                }

            }
        });

        Utils.DISPLAY_OPTIONS_VISIBILITY.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                displayOptionsLayout.setVisibility(visibility);
                switch(visibility){
                    case View.VISIBLE:
                        hamburgerMenuItem.setChecked(true);
                        Utils.CART_LIST_VISIBILITY.setValue(View.GONE);
                    case View.INVISIBLE:
                        hamburgerMenuItem.setChecked(false);
                    case View.GONE:
                        hamburgerMenuItem.setChecked(false);
                    default:
                        hamburgerMenuItem.setChecked(false);
                }

            }
        });

        Utils.HOME_OPTION_COLOR.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer color) {
                switch(color) {
                    case R.color.colorOrange:
                        homeOption.setTextColor(getResources().getColor(R.color.colorOrange, null));
                        aboutOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        contactOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                    default:

                }
            }
        });

        Utils.ABOUT_OPTION_COLOR.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer color) {
                switch(color) {
                    case R.color.colorOrange:
                        aboutOption.setTextColor(getResources().getColor(R.color.colorOrange, null));
                        homeOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        contactOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                    default:

                }
            }
        });

        Utils.CONTACT_OPTION_COLOR.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer color) {
                switch(color) {
                    case R.color.colorOrange:
                        contactOption.setTextColor(getResources().getColor(R.color.colorOrange, null));
                        homeOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        aboutOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                    default:

                }
            }
        });
    }

    private void initialiseUtils(){
        Log.v("REFERENCE : ","" + Utils.CART_LIST_VISIBILITY + " " + Utils.DISPLAY_OPTIONS_VISIBILITY + " "
                + Utils.HOME_OPTION_COLOR + " " + Utils.ABOUT_OPTION_COLOR + " " + Utils.CONTACT_OPTION_COLOR);
        Utils.CART_LIST_VISIBILITY.setValue(View.GONE);
        Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
        Utils.HOME_OPTION_COLOR.setValue(R.color.colorOrange);
        Utils.ABOUT_OPTION_COLOR.setValue(R.color.colorBlack);
        Utils.CONTACT_OPTION_COLOR.setValue(R.color.colorBlack);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        //Initialising Menu Items
        cartMenuItem = menu.findItem(R.id.action_cart);
        hamburgerMenuItem = menu.findItem(R.id.action_hamburger);
        profileMenuItem = menu.findItem(R.id.action_profile);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            if (Utils.loggedIn) {
                if (cartMenuItem.isChecked()) {
                    Utils.CART_LIST_VISIBILITY.setValue(View.GONE);
                } else {
                    Utils.CART_LIST_VISIBILITY.setValue(View.VISIBLE);
                    populateCartList1();
                }
            } else {
                Utils.setFragment(this,new LoginFragment(),false);
            }
        }
        else if (id == R.id.action_profile){
            Utils.CART_LIST_VISIBILITY.setValue(View.GONE);
            Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            Utils.setFragment(this,new LoginFragment(),false);
        }
        else if(id == R.id.action_hamburger){
            if(hamburgerMenuItem.isChecked()) Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            else Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Utils.CART_LIST_VISIBILITY.setValue(View.GONE);
        Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.display_fragment);
        if(fragment instanceof  HomeFragment)finish();
        super.onBackPressed();
    }

    private void populateCartList1(){

        Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive", "efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        final String token = response.body().getToken();
                        populateCartList2(token);
                    }

                    @Override
                    public void onFailure(Call<APITokenResult> call, Throwable t) {

                    }
                }
        );

    }

    private void populateCartList2(String token){
        Call<CartListResult> callCartProducts = Utils.getAPIInstance().getCartList("Bearer " + token, Utils.userId);
        callCartProducts.enqueue(
                new Callback<CartListResult>() {
                    @Override
                    public void onResponse(Call<CartListResult> call, Response<CartListResult> response) {
                        cartTotalPrice.setText("" + response.body().getTotalPriceList().get(0).getTotalPrice());
                        setCartListAdapter(response.body());
                    }
                    @Override
                    public void onFailure(Call<CartListResult> call, Throwable t) {

                    }
                }
        );
    }

    private void setCartListAdapter(CartListResult cartListResult) {
        cartListRecyclerView.setAdapter(new CartListAdapter(this, cartListResult));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cart_details_btn:
                cartDetailsBtnClicked();
            case R.id.home_option:
                homeOptionClicked();
            case R.id.about_option:
                aboutOptionClicked();
            case R.id.contact_option:
                contactOptionClicked();
        }
    }

}
