package com.example.kisaanonline.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.kisaanonline.ApiResults.UserDetailResult;
import com.example.kisaanonline.MainActivity;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayOnlineFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pay_online, container, false);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        PaytmPGService paytmService = PaytmPGService.getStagingService("");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", "qmrsQK63338525663017");
        paramMap.put("ORDER_ID", "20200620183925959000");
        paramMap.put("CUST_ID", "4e59566a67565852344e4b61623741535132596257773d3d");
        paramMap.put("MOBILE_NO", "7622017002");
        paramMap.put("EMAIL", "info@kisaanonline.net");
        paramMap.put("CHANNEL_ID", "WEB");
        paramMap.put("TXN_AMOUNT", "9400.0");
        paramMap.put("WEBSITE", "DEFAULT");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("CALLBACK_URL", "https://bazaar.kisaanonline.net/payment-response");
        paramMap.put("CHECKSUMHASH", "TKjOAncns2trqPsNAdYd8gcRVXgXkITcjL4H1ftYfCvVIV+9fmB8Pflr3nk0S8tRwhe0b7tgKzp5qDzlZT0jE7JODFCw6E7Jd06KD0gNErk=");
        PaytmOrder Order = new PaytmOrder((HashMap<String, String>) paramMap);

        paytmService.initialize(Order, null);

        paytmService.startPaymentTransaction(getActivity(), true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
            }

            public void onTransactionResponse(Bundle inResponse) {
                Toast.makeText(getActivity().getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                Log.v("RESPONSE : ", "" + inResponse.toString());
            }

            public void networkNotAvailable() {
            }

            public void clientAuthenticationFailed(String inErrorMessage) {
            }

            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
            }

            public void onBackPressedCancelTransaction() {
            }

            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
            }
        });
        return v;
    }

}