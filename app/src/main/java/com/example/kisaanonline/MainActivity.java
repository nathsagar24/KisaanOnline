package com.example.kisaanonline;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
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
import com.facebook.stetho.Stetho;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Don't refresh token without any reason if we find that there is no token or token has expired
// we refresh the token on the main thread and then recall the method

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout cartListLayout,displayOptionsLayout;
    private MenuItem cartMenuItem, hamburgerMenuItem, profileMenuItem;
    private TextView homeOption,aboutOption,contactOption;
    private Button cartDetailsBtn;
    private RecyclerView cartListRecyclerView;
    private RecyclerView.Adapter cartListAdapter;
    private TextView cartTotalPrice;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Utils.setFragment(MainActivity.this,new HomeFragment(),false);

        requestPermission();

        Stetho.initializeWithDefaults(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        //Initialising Menu Items
        cartMenuItem = menu.findItem(R.id.action_cart);
        hamburgerMenuItem = menu.findItem(R.id.action_hamburger);
        profileMenuItem = menu.findItem(R.id.action_profile);

        intialiseMenuButtons();

        initialiseMenuUtils();
        setListenersToUtils();

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            return;
        }
        Toast.makeText(this, "Require External Storage Write Permission", Toast.LENGTH_SHORT).show();
        requestPermission();
    }

    private void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
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
            Utils.CART_LIST_VISIBILITY.setValue(View.GONE);
            Utils.HOME_OPTION_COLOR.setValue(R.color.colorOrange);
            Utils.setFragment(this, new CartDetailsFragment(),true);
    }

    private void homeOptionClicked(){
            Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            Utils.HOME_OPTION_COLOR.setValue(R.color.colorOrange);
            Utils.setFragment(this, new HomeFragment(),true);
    }

    private void aboutOptionClicked() {
        Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
        Utils.ABOUT_OPTION_COLOR.setValue(R.color.colorOrange);
        Utils.setFragment(this, new AboutFragment(), true);
    }

    private void contactOptionClicked(){
            Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            Utils.CONTACT_OPTION_COLOR.setValue(R.color.colorOrange);
            Utils.setFragment(this, new ContactUsFragment(),true);
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
                        break;
                    case View.INVISIBLE:
                        cartMenuItem.setChecked(false);
                        break;
                    case View.GONE:
                        cartMenuItem.setChecked(false);
                        break;
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
                        break;
                    case View.INVISIBLE:
                        hamburgerMenuItem.setChecked(false);
                        break;
                    case View.GONE:
                        hamburgerMenuItem.setChecked(false);
                        break;
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
                        break;
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
                        break;
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
                        break;
                    default:

                }
            }
        });
    }

    private void initialiseMenuUtils(){
        Utils.CART_LIST_VISIBILITY.setValue(View.GONE);
        Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
        Utils.HOME_OPTION_COLOR.setValue(R.color.colorOrange);
        Utils.ABOUT_OPTION_COLOR.setValue(R.color.colorBlack);
        Utils.CONTACT_OPTION_COLOR.setValue(R.color.colorBlack);
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
                    populateCartList(Utils.token);
                }
            } else {
                Utils.setFragment(this,new LoginFragment(),true);
            }
        }
        else if (id == R.id.action_profile){
            Utils.CART_LIST_VISIBILITY.setValue(View.GONE);
            Utils.DISPLAY_OPTIONS_VISIBILITY.setValue(View.GONE);
            Utils.setFragment(this,new LoginFragment(),true);
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
        /*Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.display_fragment);
        if(fragment instanceof  HomeFragment)finish();*/
        super.onBackPressed();
    }

    private void populateCartList(String token){
        Call<CartListResult> callCartProducts = Utils.getAPIInstance().getCartList("Bearer " + token, Utils.userId);
        callCartProducts.enqueue(
                new Callback<CartListResult>() {
                    @Override
                    public void onResponse(Call<CartListResult> call, Response<CartListResult> response) {
                        if(response.code() == 200) {
                            setCartListAdapter(response.body());
                            cartTotalPrice.setText("" + response.body().getTotalPrice());
                        }
                        else if (response.code() == 401){
                            Utils.refreshToken(MainActivity.this, new Utils.TokenReceivedListener() {
                                @Override
                                public void onTokenReceived() {
                                    populateCartList(token);
                                }
                            });
                        }
                        else{
                            Toast.makeText(MainActivity.this, "API Call Succesful but Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<CartListResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setCartListAdapter(CartListResult cartListResult) {
        cartListAdapter = new CartListAdapter(this, cartListResult);
        cartListRecyclerView.setAdapter(cartListAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cart_details_btn:
                cartDetailsBtnClicked();
                break;
            case R.id.home_option:
                homeOptionClicked();
                break;
            case R.id.about_option:
                aboutOptionClicked();
                break;
            case R.id.contact_option:
                contactOptionClicked();
                break;
            default:
        }
    }

}
