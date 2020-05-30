package com.example.kisaanonline;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    private static KisaanOnlineAPI api;
    private static final String BASE_URL = "http://103.106.20.186:9009/shoppingcart_api/";
    public static boolean loggedIn;
    public static String userId;



    public static void setFragment(FragmentActivity parentActivity, Fragment newFragment, boolean addToBackStack){

        Fragment currentFragment=parentActivity.getSupportFragmentManager().findFragmentById(R.id.display_fragment);
        if(currentFragment!=null && currentFragment.getClass().equals(newFragment.getClass()))return;
        FragmentTransaction fragmentTransaction=parentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.display_fragment, newFragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static KisaanOnlineAPI getAPIInstance(){
        if(api == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .build();
            api = retrofit.create(KisaanOnlineAPI.class);
        }
        return api;
    }

}
