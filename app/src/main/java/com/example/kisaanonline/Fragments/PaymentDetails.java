package com.example.kisaanonline.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PaymentDetails extends Fragment {

    private TextView billingDetailsBtn;
    private Button screenshotChooserBtn;
    private static final int SCREENSHOT_PICKER = 1;
    private TextView screenshotFileName;
    private Button screenshotUploadBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment_details, container, false);

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
                        getActivity().getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Utils.setFragment(getActivity(), new HomeFragment(), false);
                    }
                }
        );

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCREENSHOT_PICKER && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String mimeType = getContext().getContentResolver().getType(uri);
            mimeType = mimeType.substring(mimeType.lastIndexOf('/')+1, mimeType.length());
            screenshotFileName.setText(uri.getPath().substring(uri.getPath().lastIndexOf('/')+1,uri.getPath().length()-1) + "." + mimeType);

        }
    }

}
