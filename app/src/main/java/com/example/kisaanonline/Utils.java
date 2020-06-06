package com.example.kisaanonline;

import android.app.Activity;
import android.content.Context;
import android.media.session.MediaSession;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.ApiResults.CityListResult;
import com.example.kisaanonline.ApiResults.PincodeListResult;
import com.example.kisaanonline.ApiResults.StateListResult;
import com.example.kisaanonline.Fragments.HomeFragment;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.Models.CityCredentials;
import com.example.kisaanonline.Models.PincodeCredentials;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public static void refreshToken(Context context, TokenReceivedListener listener){
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
                        refreshToken2(token,listener);
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

    public static void refreshToken2(String newToken, TokenReceivedListener listener){

        Utils.token = newToken;
        listener.onTokenReceived();
        Log.v("TOKEN UPDATED : ","" + Utils.token);
    }

    public interface TokenReceivedListener{
        void onTokenReceived();
    }

    public static void populatePincodeList(String token, String state, String city, Context context, Spinner spinner) {
        Call<PincodeListResult> callPincodeList = Utils.getAPIInstance().getPincodeList(new PincodeCredentials(state, city),"Bearer " + token);
        callPincodeList.enqueue(
                new Callback<PincodeListResult>() {
                    @Override
                    public void onResponse(Call<PincodeListResult> call, Response<PincodeListResult> response) {
                        if(response.code() == 200){
                            ArrayList<String> pincodeList = new ArrayList<>();
                            for(int i = 0; i < response.body().getData().size(); i++)
                            {
                                pincodeList.add(response.body().getData().get(i).getName());
                            }
                            ArrayAdapter<String> pincodeListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, pincodeList);
                            spinner.setAdapter(pincodeListAdapter);
                            pincodeListAdapter.notifyDataSetChanged();

                            // citySelector.setSelection(0);
                            // Log.v("CITY: ", "" +citySelector.getItemAtPosition(0).toString());
                        }
                        else{
                            Toast.makeText(context, "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PincodeListResult> call, Throwable t) {
                        Toast.makeText(context, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public static void populateCityList(String token, String state, Context context, Spinner spinner){
        Toast.makeText(context,"" + state, Toast.LENGTH_SHORT).show();
        Call<CityListResult> callCityList = Utils.getAPIInstance().getCityList(new CityCredentials(state),"Bearer " + token);
        callCityList.enqueue(
                new Callback<CityListResult>() {
                    @Override
                    public void onResponse(Call<CityListResult> call, Response<CityListResult> response) {
                        if(response.code() == 200){
                            ArrayList<String> cityList = new ArrayList<>();
                            for(int i = 0; i < response.body().getData().size(); i++)
                            {
                                cityList.add(response.body().getData().get(i).getCity());
                            }
                            ArrayAdapter<String> cityListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cityList);
                            spinner.setAdapter(cityListAdapter);
                            cityListAdapter.notifyDataSetChanged();

                            // citySelector.setSelection(0);
                            // Log.v("CITY: ", "" +citySelector.getItemAtPosition(0).toString());
                        }
                        else{
                            Toast.makeText(context, "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CityListResult> call, Throwable t) {
                        Toast.makeText(context, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public static void populateStateList(String token, Context context, Spinner spinner){
        Call<StateListResult> callStateList = Utils.getAPIInstance().getStateList("Bearer " + token);
        callStateList.enqueue(new Callback<StateListResult>() {
            @Override
            public void onResponse(Call<StateListResult> call, Response<StateListResult> response) {
                if(response.code() == 200){
                    ArrayList<String> stateList = new ArrayList<>();
                    for(int i = 0; i < response.body().getData().size(); i++)
                    {
                        stateList.add(response.body().getData().get(i).getStateName());
                    }
                    ArrayAdapter<String> stateListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, stateList);
                    spinner.setAdapter(stateListAdapter);
                    stateListAdapter.notifyDataSetChanged();
                    //stateSelector.setSelection(0);
                }
                else{
                    Toast.makeText(context, "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateListResult> call, Throwable t) {
                Toast.makeText(context, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
