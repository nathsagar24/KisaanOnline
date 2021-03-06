package com.example.kisaanonline.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.KisaanOnlineAPI;
import com.example.kisaanonline.ApiResults.LoginResult;
import com.example.kisaanonline.Models.LoginCredentials;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private Button loginBtn, registerBtn;
    private EditText username,password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Getting References`
        username=v.findViewById(R.id.user_name_or_email);
        password=v.findViewById(R.id.password);
        loginBtn = v.findViewById(R.id.login_btn);
        registerBtn = v.findViewById(R.id.register_btn);

        //Setting OnClick Listeners
        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loginBtnClicked();
                    }
                }
        );
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerBtnClicked();
            }
        });

        return v;
    }

    private void registerBtnClicked() {

        Utils.setFragment(getActivity(), new RegisterFragment(), true);

    }

    private void loginBtnClicked() {

        if(isValidInput(username.getText().toString(), password.getText().toString())){
            checkLoginCredentials(username.getText().toString(), password.getText().toString(), Utils.token);
        }
        else{
            Toast.makeText(getActivity(), "Please Input proper Credentials!!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValidInput(String user, String pass) {
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            Toast.makeText(getActivity(),"Please fill up all the credentials!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkLoginCredentials(String user, String pass, String token) {

        Call<LoginResult> callLogin = Utils.getAPIInstance().loggedIn(new LoginCredentials(user,pass),"Bearer " + token);
        callLogin.enqueue(
                new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> callLogin, Response<LoginResult> response) {
                        if (response.code() == 200) {
                            if (response.body().getIsError().equals("N")) {
                                setLoginState(response.body().getUserId(), true);
                            }
                            else if (response.code() == 401 || response.code() == 500){
                                Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                                    @Override
                                    public void onTokenReceived() {
                                        checkLoginCredentials(username.getText().toString(), password.getText().toString(), Utils.token);
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getActivity(), "Please give correct credentials! : " + response.body().getErrorString(), Toast.LENGTH_SHORT).show();
                                setLoginState(null, false);
                            }
                        } else {
                            Toast.makeText(getActivity(), "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        setLoginState(null,false);
                        Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if(!Utils.isNetworkConnected(getActivity())) {
                            Toast.makeText(getActivity(), "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
                        }
                        checkLoginCredentials(user, pass, Utils.token);
                    }
                }
        );

    }

    private void setLoginState(@Nullable String userId, boolean loginState){
        Utils.loggedIn = loginState;
        if(Utils.loggedIn){
            Utils.userId = userId;
            Utils.setPrefs("userId", Utils.userId, getActivity());
            Utils.toolbar.getMenu().findItem(R.id.action_logout).setVisible(true);
            Utils.toolbar.getMenu().findItem(R.id.action_cart).setVisible(true);
            Utils.toolbar.getMenu().findItem(R.id.action_hamburger).setVisible(true);
            Utils.toolbar.getMenu().findItem(R.id.action_profile).setVisible(false);
            Utils.setFragment(getActivity(), new HomeFragment(), true);
        }
    }

}
