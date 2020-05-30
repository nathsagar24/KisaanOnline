package com.example.kisaanonline.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.ApiResults.ProductListResult;
import com.example.kisaanonline.Adapters.ProductListAdapter;
import com.example.kisaanonline.Models.ProductListBody;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Models.SearchCredentials;
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
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        //Get References
        priceRange=v.findViewById(R.id.price_range);
        priceSeekBar=v.findViewById(R.id.price_seekbar);

        //Setting Up Product List
        productListRecyclerView = v.findViewById(R.id.products_recycler_view);
        productListLayoutManager = new LinearLayoutManager(getActivity());
        productListRecyclerView.setLayoutManager(productListLayoutManager);
        getProductList();

        searchView = v.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        Log.v("SEARCH CLICKED : ","YES");
                        getSearchedProducts();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                }
        );

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

    private void getSearchedProducts() {

        Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        final String token = response.body().getToken();
                        Call<ProductListResult> callSearchedProductsList = Utils.getAPIInstance()
                                .getSearchedProducts(new SearchCredentials(searchView.getQuery().toString()
                                                                            , priceSeekBar.getSelectedMinValue().intValue()
                                                                            , priceSeekBar.getSelectedMaxValue().intValue()
                                                                            , 0)
                                        ,"Bearer " + token);
                        callSearchedProductsList.enqueue(
                                new Callback<ProductListResult>() {
                                    @Override
                                    public void onResponse(Call<ProductListResult> call, Response<ProductListResult> response) {
                                        final List<ProductListResult.ProductDetails> productList = response.body().getData();
                                        setProductListAdapter(productList);
                                    }

                                    @Override
                                    public void onFailure(Call<ProductListResult> call, Throwable t) {

                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Call<APITokenResult> call, Throwable t) {

                    }
                }
        );

    }

    private void getProductList(){
        Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        final String token = response.body().getToken();
                        Call<ProductListResult> callProductList = Utils.getAPIInstance()
                                                                .getProductDetails(new ProductListBody(),"Bearer " + token);
                        callProductList.enqueue(
                                new Callback<ProductListResult>() {
                                    @Override
                                    public void onResponse(Call<ProductListResult> call, Response<ProductListResult> response) {
                                        final List<ProductListResult.ProductDetails> productList = response.body().getData();
                                        setProductListAdapter(productList);
                                    }

                                    @Override
                                    public void onFailure(Call<ProductListResult> call, Throwable t) {

                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Call<APITokenResult> call, Throwable t) {

                    }
                }
        );
    }

    private void setProductListAdapter(List<ProductListResult.ProductDetails> productList) {

    productListRecyclerView.setAdapter( new ProductListAdapter(getActivity(), productList) );
    }

}
