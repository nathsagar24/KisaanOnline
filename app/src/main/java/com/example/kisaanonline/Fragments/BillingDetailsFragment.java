package com.example.kisaanonline.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisaanonline.ApiResults.UserDetailResult;
import com.example.kisaanonline.Models.UserCredentials;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.kisaanonline.Utils.populateCityList;

public class BillingDetailsFragment extends Fragment {

    private TextView cartDetailsBtn;
    private Button pay;
    private Spinner stateSelector, citySelector, pincodeSelector;
    private EditText userName, phone, email, address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_billing_details, container, false);

        //Getting references
        userName = v.findViewById(R.id.name);
        phone = v.findViewById(R.id.phone_no);
        email = v.findViewById(R.id.email);
        address = v.findViewById(R.id.address);

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
        stateSelector.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        populateCityList(Utils.token, stateSelector.getSelectedItem().toString(), getActivity(), citySelector);
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
                        Utils.populatePincodeList(Utils.token, stateSelector.getSelectedItem().toString(), citySelector.getSelectedItem().toString(), getActivity(), pincodeSelector);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
        Utils.populateStateList(Utils.token, getActivity(), stateSelector);
        //Setting Up Pay Button
        pay=v.findViewById(R.id.pay);
        pay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(areFieldsEmpty()){
                            Toast.makeText(getActivity(),"Please fill up all the credentials", Toast.LENGTH_SHORT).show();
                        }
                        else if(!validCredentials()){
                            Toast.makeText(getActivity(),"Please fill valid credentials", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Utils.setFragment(getActivity(), new PaymentDetails(), true);
                        }
                        }
                }
        );

        setBillingDetails(Utils.token, Utils.userId);

        return v;
    }

    private void setBillingDetails(String token, String userId){
        Call<UserDetailResult> callUserDetail = Utils.getAPIInstance().getUserDetail("Bearer " + token, userId);
        callUserDetail.enqueue(
                new Callback<UserDetailResult>() {
                    @Override
                    public void onResponse(Call<UserDetailResult> call, Response<UserDetailResult> response) {
                        List<UserDetailResult.User> userDetailList = response.body().getUserDetail();
                        if(response.code() == 200) {
                            userName.setText(userDetailList.get(0).getName());
                            phone.setText(userDetailList.get(0).getContact());
                            email.setText(userDetailList.get(0).getEmail());
                            address.setText(userDetailList.get(0).getAddress());
                            //Set state, city and pincode
                        }
                        else if (response.code() == 401 || response.code() == 500){
                            Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                                @Override
                                public void onTokenReceived() {
                                    setBillingDetails(Utils.token, Utils.userId);
                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(), "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if(!Utils.isNetworkConnected(getActivity())) {
                            Toast.makeText(getActivity(), "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
                        }
                        setBillingDetails(Utils.token, Utils.userId);
                    }
                }
        );
    }

    private boolean areFieldsEmpty(){
        return TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) ||
                TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(address.getText().toString());
    }

    private boolean validCredentials(){
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && phone.getText().toString().length() == 10;
    }

}
