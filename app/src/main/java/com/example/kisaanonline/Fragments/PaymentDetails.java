package com.example.kisaanonline.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisaanonline.ApiResults.CheckoutResult;
import com.example.kisaanonline.Models.UserCredentials;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.security.Permission;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetails extends Fragment {

    private TextView billingDetailsBtn;
    private Button screenshotChooserBtn;
    private static final int SCREENSHOT_PICKER = 1;
    private TextView screenshotFileName;
    private Button screenshotUploadBtn;
    private File paymentPicFile, userInfoFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment_details, container, false);

        //Getting Billing Details
        Bundle bundle = getArguments();
        UserCredentials userCredentials = bundle.getParcelable("userInfo");
        Gson gson =new Gson();
        String userInfoJsonString = gson.toJson(userCredentials);

        //Get User Info Json File
        File path = getActivity().getExternalFilesDir(null);
        userInfoFile = getUserInfoFile(userInfoJsonString, path);


        //Setting Up Billing Details Btn
        billingDetailsBtn=v.findViewById(R.id.billing_details_btn);
        billingDetailsBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.setFragment(getActivity(),new BillingDetailsFragment(),true);
                    }
                }
        );

        //Setting Up Screenshot Uploading
        screenshotFileName = v.findViewById(R.id.screenshot_file_name);
        screenshotChooserBtn=v.findViewById(R.id.screenshot_chooser_btn);
        screenshotChooserBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(intent, SCREENSHOT_PICKER);
                    }
                }
        );

        screenshotUploadBtn = v.findViewById(R.id.upload_screenshot_btn);
        screenshotUploadBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        RequestBody userInfoRequestBody = RequestBody.create(userInfoFile,MediaType.parse("*/*"));
                        MultipartBody.Part userInfoFilePart = MultipartBody.Part.createFormData("json", userInfoFile.getName(), userInfoRequestBody);
                        RequestBody paymentPicRequestBody = RequestBody.create(paymentPicFile,MediaType.parse("*/*"));
                        MultipartBody.Part paymentPicFilePart = MultipartBody.Part.createFormData("file", paymentPicFile.getName(), paymentPicRequestBody);

                        uploadPaymentDetails(userInfoFilePart, paymentPicFilePart, Utils.token);

                        getActivity().getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Utils.setFragment(getActivity(), new HomeFragment(), false);
                    }
                }
        );

        return v;
    }

    private void uploadPaymentDetails(MultipartBody.Part userInfoFilePart, MultipartBody.Part paymentPicFilePart, String token) {
        Call<CheckoutResult> callPayment = Utils.getAPIInstance().uploadPaymentDetails("Bearer " + token, Utils.userId, userInfoFilePart, paymentPicFilePart);
        callPayment.enqueue(
                new Callback<CheckoutResult>() {
                    @Override
                    public void onResponse(Call<CheckoutResult> call, Response<CheckoutResult> response) {
                        if(response.code() == 200) {
                            if(response.body().getIserror().equals("N")) {
                                Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(), "Please give correct credentials! : " + response.body().getFail(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (response.code() == 401 || response.code() == 500){
                            Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                                @Override
                                public void onTokenReceived() {
                                    uploadPaymentDetails(userInfoFilePart, paymentPicFilePart, Utils.token);
                                }
                            });
                        }
                        else{
                            Log.v("UPLOAD FAILED : ","" + response.code() );
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckoutResult> call, Throwable t) {
                        Log.v("UPLOAD FAILED : ","" + t );
                    }
                }
        );
    }

    private File getUserInfoFile(String userInfoJsonString, File path) {
        File userInfoFile = new File(path, "userInfo.json");
        try {
            FileOutputStream stream = new FileOutputStream(userInfoFile);
            stream.write(userInfoJsonString.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Exception : " + e, Toast.LENGTH_SHORT).show();
        }
        finally {
            return userInfoFile;
        }
    }

   /* private void writeToFile(String userInfoJsonString) throws IOException {

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getActivity().openFileOutput("userInfo.txt", Context.MODE_PRIVATE));
        outputStreamWriter.write(userInfoJsonString);
        outputStreamWriter.close();

    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCREENSHOT_PICKER && resultCode == Activity.RESULT_OK) {
            Uri paymentPicUri = data.getData();
           getPaymentPicFile(paymentPicUri);
           /* Toast.makeText(getActivity(), "" + uri.getPath(), Toast.LENGTH_SHORT).show();
            String mimeType = getContext().getContentResolver().getType(uri);
            mimeType = mimeType.substring(mimeType.lastIndexOf('/')+1, mimeType.length());
            screenshotFileName.setText(uri.getPath().substring(uri.getPath().lastIndexOf('/')+1,uri.getPath().length()-1) + "." + mimeType);*/

        }
    }

    private void getPaymentPicFile(Uri paymentPicUri) {
        Log.v("URI Path : ","" + getRealPathFromUri(paymentPicUri) );
        paymentPicFile = new File(getRealPathFromUri(paymentPicUri));
    }

    private String getRealPathFromUri(Uri paymentPicUri) {
        String realPath;
        Cursor cursor = getActivity().getContentResolver().query(paymentPicUri, null, null, null, null, null);
        if(cursor == null){
            realPath = paymentPicUri.getPath();
        }
        else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            realPath = cursor.getString(idx);
            cursor.close();
        }
     return realPath;
    }

}
