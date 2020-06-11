package com.example.kisaanonline.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.ApiResults.CartDetailsResult;
import com.example.kisaanonline.Adapters.CartDetailsAdapter;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import java.util.List;

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
        //Utils.refreshToken(getActivity());
        getCartDetails(Utils.token);
        //Populating the data
       /* Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive", "efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        final String token = response.body().getToken();
                    }

                    @Override
                    public void onFailure(Call<APITokenResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "Call to API for token failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
*/
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

    private void getCartDetails(String token){
        Call<CartDetailsResult> callCartDetails = Utils.getAPIInstance().getCartDetails("Bearer " + token, Utils.userId);
        callCartDetails.enqueue(
                new Callback<CartDetailsResult>() {
                    @Override
                    public void onResponse(Call<CartDetailsResult> call, Response<CartDetailsResult> response) {
                        if(response.code() == 200) {
                            setCartDetailsAdapter(response.body());
                            subTotal.setText("Sub Total Rs. " + response.body().getTotalList().get(0).getSubTotal());
                            totalGst.setText("Total GST Rs. " + response.body().getTotalList().get(0).getTotalGstAmt());
                            netTotal.setText("Total Rs. " + response.body().getTotalList().get(0).getNetTotal());
                        }
                        else if (response.code() == 401 || response.code() == 500){
                            Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                                @Override
                                public void onTokenReceived() {
                                    getCartDetails(Utils.token);
                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(), "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartDetailsResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setCartDetailsAdapter(CartDetailsResult data){
        cartDetailsRecyclerView.setAdapter(new CartDetailsAdapter(getActivity(), data));
    }

}
