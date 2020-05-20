package com.example.kisaanonline.Fragments;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kisaanonline.R;

public class LoginFragment extends Fragment {
    private Button loginBtn,registerBtn;
   // private ImageView loginCloseBtn;
    private DrawerLayout drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_login, container, false);

        //Get References
        //loginCloseBtn=v.findViewById(R.id.login_close_btn);
        loginBtn=v.findViewById(R.id.login_btn);
        registerBtn=v.findViewById(R.id.register_btn);
        drawer=getActivity().findViewById(R.id.drawer_layout);

        //Set Click Listeners
       /* loginCloseBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawer.closeDrawer(GravityCompat.END);
                    }
                }
        );*/

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Login Button Clicked!!",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Register Button Clicked!!",Toast.LENGTH_SHORT).show();
                setFragment(R.id.display_fragment,new RegisterFragment(),true);
            }
        });

        return v;
    }

    private void setFragment(int id, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction=
                getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
