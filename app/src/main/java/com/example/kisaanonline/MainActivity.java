package com.example.kisaanonline;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

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

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

//Some error in backend Not giving getIsError == "Y" even after giving correct credentials
//A post request that returns product_name, image, price, quantity based on username  ( already sent without quantity)
//A get request that returns all product names along with image
//When Add To Cart is clicked in home page add product to cart with quantity 1 if already added ignore
//In cart details page when quantity is changed change quantity in cart of username with OnChangeListener
//In cart details page when close image is clicked the product for that username should be deleted




public class MainActivity extends AppCompatActivity
        {

   // private DrawerLayout drawer;
    private LinearLayout cartContent,displayOptions;
    private MenuItem cartMenuItem, hamburgerMenuItem;
    private TextView homeOption,aboutOption,contactOption;
    //private  NavigationView navigationView;
    private Button cartDetailsBtn;
    private String usernameOrEmail;

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
        SharedPreferences sharedPref=getSharedPreferences("UserCredentials",MODE_PRIVATE);
        usernameOrEmail = sharedPref.getString("Username/Email",null);
        if(usernameOrEmail!=null)return true;
        else return false;
    }

}
