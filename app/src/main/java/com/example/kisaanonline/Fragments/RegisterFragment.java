package com.example.kisaanonline.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kisaanonline.ApiResults.RegisterResult;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Models.RegistrationCredentials;
import com.example.kisaanonline.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    private Button loginBtn,registerBtn;
    private EditText name, email, mobileNo, password, confirmPassword, address ;
    private Spinner stateSelector, citySelector, pincodeSelector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        //Get References
        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        mobileNo = v.findViewById(R.id.mobile_no);
        password = v.findViewById(R.id.password);
        confirmPassword = v.findViewById(R.id.confirm_password);
        address = v.findViewById(R.id.address);

        //Setting Up selectors
        stateSelector = v.findViewById(R.id.state_selector);
        citySelector = v.findViewById(R.id.city_selector);
        pincodeSelector = v.findViewById(R.id.pincode_selector);
        stateSelector.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        Toast.makeText(getActivity(),"STATE SELECTED",Toast.LENGTH_SHORT).show();
                        Utils.populateCityList(Utils.token, stateSelector.getSelectedItem().toString(), getActivity(), citySelector);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
        Utils.populateStateList(Utils.token, getActivity(), stateSelector);
        //Setting Up city selector
        citySelector.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        Utils.populatePincodeList(Utils.token, stateSelector.getSelectedItem().toString(), citySelector.getSelectedItem().toString(), getActivity(), pincodeSelector);
                        /*Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                            @Override
                            public void onTokenReceived() {
                                Utils.populatePincodeList(Utils.token, stateSelector.getSelectedItem().toString(), citySelector.getSelectedItem().toString(), getActivity(), pincodeSelector);
                            }
                        });*/
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );


        //Setting Up Login
        loginBtn=v.findViewById(R.id.login_btn);
        registerBtn=v.findViewById(R.id.register_btn);
        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.setFragment(getActivity(),new LoginFragment(),true);
                    }
                }
        );

        registerBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Utils.refreshToken(getActivity());
                        register(Utils.token);
                       /* Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                            @Override
                            public void onTokenReceived() {
                                register(Utils.token);
                            }
                        });*/

                    }
                }
        );

        return v;
    }

    private boolean areFieldsEmpty(){
        return TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) ||
                TextUtils.isEmpty(mobileNo.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) ||
                TextUtils.isEmpty(confirmPassword.getText().toString()) || TextUtils.isEmpty(address.getText().toString());
    }

    private boolean validRegistration(){
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && mobileNo.getText().toString().length() == 10
                && password.getText().toString().equals(confirmPassword.getText().toString());
    }

    private void register(String token) {
        if(areFieldsEmpty()){
            Toast.makeText(getActivity(),"Please fill up all the credentials!",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!validRegistration()){
            Toast.makeText(getActivity(),"Please give correct credentials!",Toast.LENGTH_SHORT).show();
            return;
        }
        Call<RegisterResult> callRegister = Utils.getAPIInstance()
                .register(new RegistrationCredentials(name.getText().toString(), email.getText().toString(), password.getText().toString()
                        , mobileNo.getText().toString(), address.getText().toString(), stateSelector.getSelectedItem().toString(),
                        citySelector.getSelectedItem().toString(), pincodeSelector.getSelectedItem().toString()),"Bearer " + token);
        callRegister.enqueue(
                new Callback<RegisterResult>() {
                    @Override
                    public void onResponse(Call<RegisterResult> callRegister, Response<RegisterResult> response) {
                        if (response.code() == 200) {
                            if (response.body().getIsError().equals("N")) {
                                //isRegistered(true);
                                Utils.setFragment(getActivity(), new LoginFragment(), true);
                            }
                            else if (response.code() == 401 || response.code() == 500){
                                Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                                    @Override
                                    public void onTokenReceived() {
                                       register(Utils.token);
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getActivity(), "Please give valid credentials! : " + response.body().getErrorString(), Toast.LENGTH_SHORT).show();
                                //isRegistered(false);
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                       // isRegistered(false);
                    }
                }
        );
        }

}