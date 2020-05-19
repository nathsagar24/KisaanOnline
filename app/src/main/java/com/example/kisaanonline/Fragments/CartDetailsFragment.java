package com.example.kisaanonline.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.kisaanonline.R;

public class CartDetailsFragment extends Fragment {

    private Button checkoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart_details, container, false);

        checkoutBtn=v.findViewById(R.id.checkout_btn);
        checkoutBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction fragmentTransaction=getActivity()
                                                                .getSupportFragmentManager()
                                                                .beginTransaction()
                                                                .replace(R.id.display_fragment,new BillingDetailsFragment())
                                                                .addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
        );

        return v;
    }

}
