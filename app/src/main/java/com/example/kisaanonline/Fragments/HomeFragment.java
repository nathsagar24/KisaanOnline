package com.example.kisaanonline.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.kisaanonline.APIToken;
import com.example.kisaanonline.AuthenticationCredentials;
import com.example.kisaanonline.Authorizer;
import com.example.kisaanonline.ProductDetailsList;
import com.example.kisaanonline.ProductListAdapter;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private CrystalRangeSeekbar priceSeekBar;
    private TextView priceRange;
    private RecyclerView productListRecyclerView;
    private RecyclerView.LayoutManager productListLayoutManager;
    private RecyclerView.Adapter productListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);

        //Get References
        priceRange=v.findViewById(R.id.price_range);
        priceSeekBar=v.findViewById(R.id.price_seekbar);

        //Setting Up Product List
        productListRecyclerView = v.findViewById(R.id.products_recycler_view);
        productListLayoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        productListRecyclerView.setLayoutManager(productListLayoutManager);
        getProductList();

        initialisePriceSeekBar(100,4000);

        return v;
    }

    private void initialisePriceSeekBar(int minValue, int maxValue) {

        priceSeekBar.setOnRangeSeekbarChangeListener(
                new OnRangeSeekbarChangeListener() {
                    @Override
                    public void valueChanged(Number minValue, Number maxValue) {
                        priceRange.setText("Filter: Rs. " + minValue + "- Rs. " + maxValue);
                    }
                }
        );

    }

    private void getProductList(){
        Call<APIToken> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(
                new Callback<APIToken>() {
                    @Override
                    public void onResponse(Call<APIToken> call, Response<APIToken> response) {
                        final String token = response.body().getToken();
                        Call<ProductDetailsList> callProductList = Utils.getAPIInstance()
                                                                .getProductDetails(new Authorizer("alpesh") , "Bearer " + token);
                        callProductList.enqueue(
                                new Callback<ProductDetailsList>() {
                                    @Override
                                    public void onResponse(Call<ProductDetailsList> call, Response<ProductDetailsList> response) {
                                        final List<ProductDetailsList.ProductDetails> productList = response.body().getData();
                                        setProductListAdapter(productList);
                                    }

                                    @Override
                                    public void onFailure(Call<ProductDetailsList> call, Throwable t) {

                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Call<APIToken> call, Throwable t) {

                    }
                }
        );
    }

    private void setProductListAdapter(List<ProductDetailsList.ProductDetails> productList) {

    productListRecyclerView.setAdapter( new ProductListAdapter(getActivity(), productList) );
    }

}
