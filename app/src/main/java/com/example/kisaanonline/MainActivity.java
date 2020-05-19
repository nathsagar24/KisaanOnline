package com.example.kisaanonline;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.kisaanonline.Fragments.CartDetailsFragment;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private LinearLayout cartContent,displayOptions;
    private MenuItem cartMenuItem, hamburgerMenuItem;
    private TextView homeOption;
    private  NavigationView navigationView;
    private Button cartDetailsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting the references
        cartContent=findViewById(R.id.cart_content);
        displayOptions=findViewById(R.id.display_options_layout);
        homeOption=findViewById(R.id.home_option);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        cartDetailsBtn=findViewById(R.id.cart_details_btn);

        //Setting OnClick Listener
        cartDetailsBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cartContent.setVisibility(View.GONE);
                        cartMenuItem.setChecked(false);
                        setFragment(R.id.display_fragment, new CartDetailsFragment(),true);
                    }
                }
        );

        //Initialising
        homeOption.setTextColor(getResources().getColor(R.color.colorOrange,null));

        //Initialising Action Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       //Setting Up Navigation Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(R.id.nav_view,new LoginFragment(),false);

        setFragment(R.id.display_fragment,new HomeFragment(),true);
    }

    private void setFragment(int id, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(id, fragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
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
                Toast.makeText(this,"You Are Not Logged In",Toast.LENGTH_SHORT).show();
                drawer.openDrawer(GravityCompat.END);
                displayOptions.setVisibility(View.GONE);
                hamburgerMenuItem.setChecked(false);
            }

        }
        else if (id == R.id.action_profile){
            drawer.openDrawer(GravityCompat.END);
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
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return true;
    }
}
