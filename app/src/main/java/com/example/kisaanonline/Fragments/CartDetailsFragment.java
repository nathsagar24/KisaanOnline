package com.example.kisaanonline.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisaanonline.APIToken;
import com.example.kisaanonline.AuthenticationCredentials;
import com.example.kisaanonline.CartDetails;
import com.example.kisaanonline.CartDetailsAdapter;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartDetailsFragment extends Fragment {

    private Button checkoutBtn;
   private RecyclerView cartDetailsRecyclerView;
   private TextView subTotal, totalGst, netTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart_details, container, false);

        //Getting references
        subTotal = v.findViewById(R.id.sub_total);
        totalGst = v.findViewById(R.id.total_gst);
        netTotal = v.findViewById(R.id.total);

        //Setting Up Recycler View
        cartDetailsRecyclerView = v.findViewById(R.id.cart_product_details_recycler_view);
        cartDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populating the data
        Call<APIToken> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive", "efive123"));
        callToken.enqueue(
                new Callback<APIToken>() {
                    @Override
                    public void onResponse(Call<APIToken> call, Response<APIToken> response) {
                        final String token = response.body().getToken();
                        Call<CartDetails> callCartDetails = Utils.getAPIInstance().getCartDetails("Bearer " + token, Utils.userId);
                        callCartDetails.enqueue(
                                new Callback<CartDetails>() {
                                    @Override
                                    public void onResponse(Call<CartDetails> call, Response<CartDetails> response) {
                                        if(response.code() == 200) {
                                            Toast.makeText(getActivity(), "Successfully Fetched Cart Details Data", Toast.LENGTH_SHORT).show();
                                            setCartDetailsAdapter(response.body().getDataList());
                                            subTotal.setText("Sub Total Rs. " + response.body().getTotalList().get(0).getSubTotal());
                                            totalGst.setText("Total GST Rs. " + response.body().getTotalList().get(0).getTotalGstAmt());
                                            netTotal.setText("Total Rs. " + response.body().getTotalList().get(0).getNetTotal());
                                        }
                                        else{
                                            Toast.makeText(getActivity(), "Response Received but Error Occured : " + response.errorBody(),Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CartDetails> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Call to API for Cart Details Failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Call<APIToken> call, Throwable t) {
                        Toast.makeText(getActivity(), "Call to API for token failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        checkoutBtn=v.findViewById(R.id.checkout_btn);
        checkoutBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.setFragment(getActivity(),new BillingDetailsFragment(),true);
                    }
                }
        );

        return v;
    }

    private void setCartDetailsAdapter(List<CartDetails.Data> data){
        cartDetailsRecyclerView.setAdapter(new CartDetailsAdapter(getActivity(), data));
    }

}
