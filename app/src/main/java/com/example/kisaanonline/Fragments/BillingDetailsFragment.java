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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import okhttp3.internal.Util;

import static com.example.kisaanonline.Utils.populateCityList;

public class BillingDetailsFragment extends Fragment {

    private TextView cartDetailsBtn;
    private Button pay;
    private Spinner stateSelector, citySelector, pincodeSelector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_billing_details, container, false);
        //Setting Up Cart Details Button
        cartDetailsBtn=v.findViewById(R.id.cart_details_btn);
        cartDetailsBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.setFragment(getActivity(),new CartDetailsFragment(),true);
                    }
                }
        );
        //Setting Up Selectors
        stateSelector = v.findViewById(R.id.state_selector_1);
        citySelector = v.findViewById(R.id.city_selector_1);
        pincodeSelector = v.findViewById(R.id.pincode_selector_1);
        Log.v("STATE SELECTOR : ", "" + stateSelector);
        stateSelector.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                                    @Override
                                    public void onTokenReceived() {
                                        populateCityList(Utils.token, stateSelector.getSelectedItem().toString(), getActivity(), citySelector);
                                    }
                                });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
        citySelector.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                            @Override
                            public void onTokenReceived() {
                                Utils.populatePincodeList(Utils.token, stateSelector.getSelectedItem().toString(), citySelector.getSelectedItem().toString(), getActivity(), pincodeSelector);
                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
        Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
            @Override
            public void onTokenReceived() {
                Utils.populateStateList(Utils.token, getActivity(), stateSelector);
            }
        });
        //Setting Up Pay Button
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
