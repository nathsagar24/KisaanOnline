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

import com.example.kisaanonline.APIToken;
import com.example.kisaanonline.AuthenticationCredentials;
import com.example.kisaanonline.KisaanOnlineAPI;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    private Button loginBtn, registerBtn;
    // private ImageView loginCloseBtn;
    private DrawerLayout drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Get References
        //loginCloseBtn=v.findViewById(R.id.login_close_btn);
        loginBtn = v.findViewById(R.id.login_btn);
        registerBtn = v.findViewById(R.id.register_btn);
        drawer = getActivity().findViewById(R.id.drawer_layout);

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Login Button Clicked!!", Toast.LENGTH_SHORT).show();
                        authenticate();
                    }
                }
        );
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Register Button Clicked!!", Toast.LENGTH_SHORT).show();
                Utils.setFragment(getActivity(), new RegisterFragment(), true);
            }
        });

        return v;
    }

    private void authenticate() {

        final KisaanOnlineAPI api = Utils.getAPIInstance();
        Call<APIToken> call = api.getToken(new AuthenticationCredentials("efive","efive123"));

        call.enqueue(new Callback<APIToken>() {
            @Override
            public void onResponse(Call<APIToken> call, Response<APIToken> response) {
                String apiToken = response.body().getToken();
                Toast.makeText(getActivity(),"API Token : " + apiToken, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<APIToken> call, Throwable t) {
                Toast.makeText(getActivity(), "Call to KisaanOnline API Failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
