package com.example.kisaanonline;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.example.kisaanonline.Fragments.AboutFragment;
import com.example.kisaanonline.Fragments.CartDetailsFragment;
import com.example.kisaanonline.Fragments.ContactUsFragment;
import com.example.kisaanonline.Fragments.HomeFragment;
import com.example.kisaanonline.Fragments.LoginFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Some error in backend Not giving getIsError == "Y" even after giving correct credentials
//A post request that returns product_name, image, price, quantity based on username  ( already sent without quantity)
//A get request that returns all product names along with image
//When Add To Cart is clicked in home page add product to cart with quantity 1 if already added ignore
//In cart details page when quantity is changed change quantity in cart of username with OnChangeListener
//In cart details page when close image is clicked the product for that username should be deleted




public class MainActivity extends AppCompatActivity
        {

    private LinearLayout cartContent,displayOptions;
    private MenuItem cartMenuItem, hamburgerMenuItem;
    private TextView homeOption,aboutOption,contactOption;
    private Button cartDetailsBtn;
    private RecyclerView cartRecyclerView;
    private TextView cartTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);

        //Getting the references
        cartContent=findViewById(R.id.cart_content);
        displayOptions=findViewById(R.id.display_options_layout);
        homeOption=findViewById(R.id.home_option);
        aboutOption=findViewById(R.id.about_option);
        contactOption=findViewById(R.id.contact_option);
        cartDetailsBtn=findViewById(R.id.cart_details_btn);
        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartTotalPrice = findViewById(R.id.total_cart_amount);

        //Setting OnClick Listener
        cartDetailsBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cartContent.setVisibility(View.GONE);
                        cartMenuItem.setChecked(false);
                        Utils.setFragment(MainActivity.this, new CartDetailsFragment(),true);
                    }
                }
        );

        //Initialising
        homeOption.setTextColor(getResources().getColor(R.color.colorOrange,null));

        aboutOption.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aboutOption.setTextColor(getResources().getColor(R.color.colorOrange, null));
                        homeOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        contactOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        displayOptions.setVisibility(View.GONE);
                        hamburgerMenuItem.setChecked(false);
                        Utils.setFragment(MainActivity.this, new AboutFragment(),true);
                    }
                }
        );

        contactOption.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        contactOption.setTextColor(getResources().getColor(R.color.colorOrange, null));
                        homeOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        aboutOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        displayOptions.setVisibility(View.GONE);
                        hamburgerMenuItem.setChecked(false);
                        Utils.setFragment(MainActivity.this, new ContactUsFragment(),true);
                    }
                }
        );

        homeOption.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        homeOption.setTextColor(getResources().getColor(R.color.colorOrange, null));
                        contactOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        aboutOption.setTextColor(getResources().getColor(R.color.colorBlack, null));
                        displayOptions.setVisibility(View.GONE);
                        hamburgerMenuItem.setChecked(false);
                        Utils.setFragment(MainActivity.this, new HomeFragment(),true);
                    }
                }
        );

        //Initialising Action Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Utils.setFragment(MainActivity.this,new LoginFragment(),false);
        Utils.setFragment(MainActivity.this,new HomeFragment(),true);


    }

    private void setCartProductListAdapter(CartProducts cartProducts) {
            cartRecyclerView.setAdapter(new CartProductListAdapter(this, cartProducts));
    }

            @Override
    public void onBackPressed() {
        displayOptions.setVisibility(View.GONE);
        hamburgerMenuItem.setChecked(false);
        cartContent.setVisibility(View.GONE);
        cartMenuItem.setChecked(false);
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.display_fragment);
        if(fragment instanceof  HomeFragment)finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        cartMenuItem=menu.findItem(R.id.action_cart);
        hamburgerMenuItem=menu.findItem(R.id.action_hamburger);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            Toast.makeText(this, "Cart Action Button Clicked!!", Toast.LENGTH_SHORT).show();

            if (loggedIn()) {
                if (cartMenuItem.isChecked()) {
                    cartContent.setVisibility(View.GONE);
                    cartMenuItem.setChecked(false);
                } else {
                    displayOptions.setVisibility(View.GONE);
                    hamburgerMenuItem.setChecked(false);
                    cartContent.setVisibility(View.VISIBLE);
                    cartMenuItem.setChecked(true);

                        Call<APIToken> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive", "efive123"));
                        callToken.enqueue(
                                new Callback<APIToken>() {
                                    @Override
                                    public void onResponse(Call<APIToken> call, Response<APIToken> response) {
                                        String token = response.body().getToken();
                                        Call<CartProducts> callCartProducts = Utils.getAPIInstance().getCartProductList("Bearer " + token, Utils.userId);
                                        callCartProducts.enqueue(
                                                new Callback<CartProducts>() {
                                                    @Override
                                                    public void onResponse(Call<CartProducts> call, Response<CartProducts> response) {
                                                        Log.v("RESPONSE: ", "Cart List Display");
                                                        cartTotalPrice.setText("" + response.body().getTotalPriceList().get(0).getTotalPrice());
                                                        setCartProductListAdapter(response.body());
                                                    }
                                                    @Override
                                                    public void onFailure(Call<CartProducts> call, Throwable t) {

                                                    }
                                                }
                                        );
                                    }

                                    @Override
                                    public void onFailure(Call<APIToken> call, Throwable t) {

                                    }
                                }
                        );

                }
            } else {
                Utils.setFragment(MainActivity.this,new LoginFragment(),false);
                /*Toast.makeText(this,"You Are Not Logged In",Toast.LENGTH_SHORT).show();
                drawer.openDrawer(GravityCompat.END);*/
                displayOptions.setVisibility(View.GONE);
                hamburgerMenuItem.setChecked(false);
            }

        }
        else if (id == R.id.action_profile){
            Utils.setFragment(MainActivity.this,new LoginFragment(),false);
        }
        else if(id == R.id.action_hamburger){
            Toast.makeText(this,"Hamburger Action Button Clicked!!",Toast.LENGTH_SHORT).show();
            if(hamburgerMenuItem.isChecked()){
                displayOptions.setVisibility(View.GONE);
                hamburgerMenuItem.setChecked(false);
            }
            else{
                cartContent.setVisibility(View.GONE);
                cartMenuItem.setChecked(false);
                displayOptions.setVisibility(View.VISIBLE);
                hamburgerMenuItem.setChecked(true);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean loggedIn() {
        if(Utils.loggedIn == true)return true;
        return false;
    }

}
