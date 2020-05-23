package com.example.kisaanonline.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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

import com.example.kisaanonline.APIToken;
import com.example.kisaanonline.AuthenticationCredentials;
import com.example.kisaanonline.KisaanOnlineAPI;
import com.example.kisaanonline.LoginAndRegisterResult;
import com.example.kisaanonline.LoginCredentials;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private Button loginBtn, registerBtn;
    private EditText username,password;
    private DrawerLayout drawer;
    private static final KisaanOnlineAPI api = Utils.getAPIInstance();
    private boolean loggedIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        username=v.findViewById(R.id.user_name_or_email);
        password=v.findViewById(R.id.password);
        loginBtn = v.findViewById(R.id.login_btn);
        registerBtn = v.findViewById(R.id.register_btn);
        drawer = getActivity().findViewById(R.id.drawer_layout);

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Login Button Clicked!!", Toast.LENGTH_SHORT).show();
                        if(credentialsValid()){
                            SharedPreferences sharedPref = getActivity().getSharedPreferences("User Credentials", Context.MODE_PRIVATE);
                            sharedPref.edit().putString("Username/Email",username.getText().toString()).apply();
                            Utils.setFragment(getActivity(), new HomeFragment(), true);
                        }
                    }
                }
        );
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Register Button Clicked!!", Toast.LENGTH_SHORT).show();
                Utils.setFragment(getActivity(), new RegisterFragment(), false);
            }
        });

        return v;
    }

    private boolean credentialsValid() {
        String user = username.getText().toString(), pass = password.getText().toString();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            Toast.makeText(getActivity(),"Please fill up all the credentials!",Toast.LENGTH_SHORT).show();
            return false;
        }
        validate(user, pass);
        if(!loggedIn){
            return false;
        }
        return true;
    }

    private void isLoggedIn(boolean loginState){loggedIn = loginState;}

    private void validate(String user, String pass) {
        Call<APIToken> callToken = api.getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(new Callback<APIToken>() {
            @Override
            public void onResponse(Call<APIToken> callToken, Response<APIToken> response) {
                final String token = response.body().getToken();
                Call<LoginAndRegisterResult> callLogin = api.loggedIn(new LoginCredentials(user,pass),"Bearer " + token);
                Log.v("CALL LOGIN : ", "" + callLogin);
                Log.v("TOKEN : ", "" + token);
                callLogin.enqueue(
                        new Callback<LoginAndRegisterResult>() {
                            @Override
                            public void onResponse(Call<LoginAndRegisterResult> callLogin, Response<LoginAndRegisterResult> response) {
                                Log.v("CALL LOGIN RESPONSE : ","" + response.code() + response.message());
                                if(response.body().getIsError().equals("N")){
                                    Toast.makeText(getActivity(), "You are successfully logged in",Toast.LENGTH_SHORT).show();
                                    isLoggedIn(true);
                                }
                                else {
                                    Toast.makeText(getActivity(),"Please give correct credentials!",Toast.LENGTH_SHORT).show();
                                    isLoggedIn(false);}
                            }

                            @Override
                            public void onFailure(Call<LoginAndRegisterResult> call, Throwable t) {
                                Toast.makeText(getActivity(),"Logging In Failed : " + t.getMessage(),Toast.LENGTH_SHORT).show();
                                isLoggedIn(false);
                            }
                        }
                );
            }

            @Override
            public void onFailure(Call<APIToken> call, Throwable t) {
                Toast.makeText(getActivity(), "Call to KisaanOnline API Failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
