package com.example.kisaanonline;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.Models.AuthenticationCredentials;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

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
    public static MutableLiveData<Integer> CART_LIST_VISIBILITY = new MutableLiveData<>(), DISPLAY_OPTIONS_VISIBILITY = new MutableLiveData<>()
            , HOME_OPTION_COLOR = new MutableLiveData<>(), ABOUT_OPTION_COLOR = new MutableLiveData<>(), CONTACT_OPTION_COLOR = new MutableLiveData<>();
    public static String token;

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

    public static void refreshToken(Context context){
            Call<APITokenResult> callToken = getAPIInstance().getToken(new AuthenticationCredentials("efive", "efive123"));
            callToken.enqueue(new Callback<APITokenResult>() {
                @Override
                public void onResponse(Call<APITokenResult> callToken, Response<APITokenResult> response) {
                    if (response.code() == 200) {
                        final String token = response.body().getToken();
                       /* final String expiryDateTimeString =response.body().getExpiry();
                        try {
                            Date expiryDateTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(expiryDateTimeString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }*/
                        refreshToken2(token);
                    } else {
                        Toast.makeText(context, "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<APITokenResult> call, Throwable t) {
                    Toast.makeText(context, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    public static void refreshToken2(String newToken){
        Utils.token = newToken;
    }

}
