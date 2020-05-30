package com.example.kisaanonline.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.KisaanOnlineAPI;
import com.example.kisaanonline.ApiResults.RegisterResult;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Models.RegistrationCredentials;
import com.example.kisaanonline.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    private Button loginBtn,registerBtn;
    private EditText name, email, mobileNo, password, confirmPassword, address, pincode;
    private Spinner stateSelector, citySelector;
    private DrawerLayout drawer;
    private boolean registered;
    private static final KisaanOnlineAPI api = Utils.getAPIInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        //Get References
        drawer = getActivity().findViewById(R.id.drawer_layout);
        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        mobileNo = v.findViewById(R.id.mobile_no);
        password = v.findViewById(R.id.password);
        confirmPassword = v.findViewById(R.id.confirm_password);
        address = v.findViewById(R.id.address);
        stateSelector = v.findViewById(R.id.state_selector);
        citySelector = v.findViewById(R.id.city_selector);
        pincode = v.findViewById(R.id.pincode);
        loginBtn=v.findViewById(R.id.login_btn);
        registerBtn=v.findViewById(R.id.register_btn);

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Login Button Clicked",Toast.LENGTH_SHORT).show();
                        Utils.setFragment(getActivity(),new LoginFragment(),false);
                    }
                }
        );

        registerBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Register Button Clicked",Toast.LENGTH_SHORT).show();
                        if(registrationSuccesful()){
                            SharedPreferences sharedPref = getActivity().getSharedPreferences("User Credentials", Context.MODE_PRIVATE);
                            sharedPref.edit().putString("Username/Email",name.getText().toString()).apply();
                            Utils.setFragment(getActivity(), new HomeFragment(), true);
                        }

                    }
                }
        );

        return v;
    }

    private boolean fieldsEmpty(){
        return TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) ||
                TextUtils.isEmpty(mobileNo.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) ||
                TextUtils.isEmpty(confirmPassword.getText().toString()) || TextUtils.isEmpty(address.getText().toString()) ||
                TextUtils.isEmpty(pincode.getText().toString());
    }

    private boolean validRegistration(){
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && mobileNo.getText().toString().length() == 10
                && password.getText().toString().equals(confirmPassword.getText().toString()) && pincode.getText().toString().length() == 6;
    }


    private boolean registrationSuccesful() {
        if(fieldsEmpty()){
            Toast.makeText(getActivity(),"Please fill up all the credentials!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!validRegistration()){
            Toast.makeText(getActivity(),"Please give correct credentials!",Toast.LENGTH_SHORT).show();
            return false;
        }
        validate(name.getText().toString(), email.getText().toString(), password.getText().toString(),
                mobileNo.getText().toString(), address.getText().toString(), stateSelector.getSelectedItem().toString(),
                citySelector.getSelectedItem().toString(), pincode.getText().toString());
        if(!registered){
            return false;
        }
        return true;
        }

    private void isRegistered(boolean registrationState){registered = registrationState;}

    private void validate(String user, String email, String pass, String mobileNo, String address,String state, String city, String pincode) {
        Call<APITokenResult> callToken = api.getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(new Callback<APITokenResult>() {
            @Override
            public void onResponse(Call<APITokenResult> callToken, Response<APITokenResult> response) {
                final String token = response.body().getToken();
                Call<RegisterResult> callRegister = api
                        .register(new RegistrationCredentials(user, email, pass, mobileNo, address, state, city, pincode),"Bearer " + token);
                callRegister.enqueue(
                        new Callback<RegisterResult>() {
                            @Override
                            public void onResponse(Call<RegisterResult> callRegister, Response<RegisterResult> response) {
                                Log.v("CALL LOGIN RESPONSE : ","" + response.code() + response.message());
                                if(response.body().getIsError().equals("N")){
                                    Toast.makeText(getActivity(), "You are successfully registerd!",Toast.LENGTH_SHORT).show();
                                    isRegistered(true);
                                }
                                else {
                                    isRegistered(false);}
                            }

                            @Override
                            public void onFailure(Call<RegisterResult> call, Throwable t) {
                                Toast.makeText(getActivity(),"Register Failed : " + t.getMessage(),Toast.LENGTH_SHORT).show();
                                isRegistered(false);
                            }
                        }
                );
            }

            @Override
            public void onFailure(Call<APITokenResult> call, Throwable t) {
                Toast.makeText(getActivity(), "Call to KisaanOnline API Failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





}