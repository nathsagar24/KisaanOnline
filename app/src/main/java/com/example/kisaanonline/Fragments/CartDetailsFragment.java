package com.example.kisaanonline.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

public class CartDetailsFragment extends Fragment {

    private Button checkoutBtn;
    private String productId, variantId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart_details, container, false);
        productId = getArguments().getString("productid",null);
        variantId = getArguments().getString("variantid",null);
        Log.v("PRODUCT ID : ", "" + productId);
        Log.v("VARIANT ID : ", "" + variantId);

        checkoutBtn=v.findViewById(R.id.checkout_btn);
        checkoutBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.setFragment(getActivity(),new BillingDetailsFragment(),true);
                    }
                }
        );

        return v;
    }

}
