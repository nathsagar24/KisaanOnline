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

import com.example.kisaanonline.Fragments.LoginFragment;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;


public class RegisterFragment extends Fragment {

    private Button loginBtn,registerBtn;
    //private ImageView registerCloseBtn;
    private DrawerLayout drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        //Get References
        drawer = getActivity().findViewById(R.id.drawer_layout);
        //registerCloseBtn=v.findViewById(R.id.register_close_btn);
        loginBtn=v.findViewById(R.id.login_btn);
        registerBtn=v.findViewById(R.id.register_btn);

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Login Button Clicked",Toast.LENGTH_SHORT).show();
                        Utils.setFragment(getActivity(),new LoginFragment(),true);
                    }
                }
        );

        registerBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Register Button Clicked",Toast.LENGTH_SHORT).show();
                    }
                }
        );

        return v;
    }

}