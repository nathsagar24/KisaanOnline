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
import android.widget.TextView;

import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

public class BillingDetailsFragment extends Fragment {

    private TextView cartDetailsBtn;
    private Button pay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_billing_details, container, false);
        cartDetailsBtn=v.findViewById(R.id.cart_details_btn);
        cartDetailsBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.setFragment(getActivity(),new CartDetailsFragment(),true);
                    }
                }
        );

        pay=v.findViewById(R.id.pay);
        pay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.setFragment(getActivity(),new PaymentDetails(),true);
                    }
                }
        );
        return v;
    }

}
