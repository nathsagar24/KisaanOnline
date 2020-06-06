package com.example.kisaanonline.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.kisaanonline.ApiResults.MaxPriceResult;
import com.example.kisaanonline.ApiResults.ProductListResult;
import com.example.kisaanonline.Adapters.ProductListAdapter;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Models.SearchCredentials;
import com.example.kisaanonline.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Setting Up not refreshing token without any reason but only when failed
//Only Home Fragment and MainActivity done but giving timeout
public class HomeFragment extends Fragment {
    private CrystalRangeSeekbar priceSeekBar;
    private TextView priceRange;
    private RecyclerView productListRecyclerView;
    private RecyclerView.LayoutManager productListLayoutManager;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);

        //Setting Up Product List
        productListRecyclerView = v.findViewById(R.id.products_recycler_view);
        productListLayoutManager = new LinearLayoutManager(getActivity());
        productListRecyclerView.setLayoutManager(productListLayoutManager);
       /* Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
            @Override
            public void onTokenReceived() {
                getProductList(Utils.token);
            }
        });*/
        getProductList(Utils.token);

        //Setting Up Search
        searchView = v.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String searchKeyword) {
                        hideSoftKeyboard(getActivity(), searchView.getWindowToken());
                        if(searchKeyword.isEmpty()) {Toast.makeText(getActivity(), "Please Enter Something!!",Toast.LENGTH_SHORT).show();return false;}
                        getSearchedProducts(searchKeyword, Utils.token);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String searchKeyword) {
                        getSearchedProducts(searchKeyword, Utils.token);
                        return true;
                    }
                }
        );
        searchView.setOnCloseListener(
                new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        getProductList(Utils.token);
                        return true;
                    }
                }
        );



        //Setting Up Price Seekbar
        priceRange = v.findViewById(R.id.price_range);
        priceSeekBar = v.findViewById(R.id.price_seekbar);
       /* Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
            @Override
            public void onTokenReceived() {
                Log.v("MAX PRICE TOKEN 0 : ", "" + Utils.token);
                setMaxprice(Utils.token);
            }
        });*/
        setMaxprice(Utils.token);
        setPriceSeekbarListener();
        return v;
    }

    private void hideSoftKeyboard(Activity activity, IBinder windowToken){

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0);

    }

    private void setMaxprice(String token){
        Call<MaxPriceResult> callMaxPrice = Utils.getAPIInstance().getMaxPrice("Bearer " + token);
        callMaxPrice.enqueue(new Callback<MaxPriceResult>() {
            @Override
            public void onResponse(Call<MaxPriceResult> call, Response<MaxPriceResult> response) {
                if(response.code() == 200) {
                    priceRange.setText("Filter: Rs. 0 - Rs. " + response.body().getMaxPrice());
                    priceSeekBar.setMaxValue(response.body().getMaxPrice());
                }
                else if (response.code() == 401 || response.code() == 500){
                    Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                        @Override
                        public void onTokenReceived() {
                            setMaxprice(Utils.token);
                        }
                    });
                }
                else{

                    Toast.makeText(getActivity(), "Max Price API Call Succesful but Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MaxPriceResult> call, Throwable t) {
                Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPriceSeekbarListener() {
        priceSeekBar.setOnRangeSeekbarChangeListener(
                new OnRangeSeekbarChangeListener() {
                    @Override
                    public void valueChanged(Number minValue, Number maxValue) {
                        priceRange.setText("Filter: Rs. " + minValue + "- Rs. " + maxValue);
                    }
                }
        );

    }

    private void getSearchedProducts(String searchKeyword, String token) {
        Call<ProductListResult> callSearchedProductsList = Utils.getAPIInstance()
                .getSearchedProducts(new SearchCredentials(searchKeyword
                                , 0, 100)
                        ,"Bearer " + token);
        callSearchedProductsList.enqueue(
                new Callback<ProductListResult>() {
                    @Override
                    public void onResponse(Call<ProductListResult> call, Response<ProductListResult> response) {
                        if(response.code() == 200) {
                            setProductListAdapter(response.body());
                        }
                        else if (response.code() == 401 || response.code() == 500){
                            Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                                @Override
                                public void onTokenReceived() {
                                    getSearchedProducts(searchKeyword, Utils.token);
                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(), "Search Products API Call Succesful but Error: " + response.code() , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductListResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void getProductList(String token) {
        Call<ProductListResult> callProductList = Utils.getAPIInstance()
                .getProductList(new SearchCredentials("", 0, 100),
                        "Bearer " + token);
        callProductList.enqueue(
                new Callback<ProductListResult>() {
                    @Override
                    public void onResponse(Call<ProductListResult> call, Response<ProductListResult> response) {
                        if(response.code() == 200) {
                            if(response.body().getIsError().equals("N")) {
                                setProductListAdapter(response.body());
                            }
                            else{
                                Toast.makeText(getActivity(), "Please give correct credentials!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (response.code() == 401 || response.code() == 500){
                            Utils.refreshToken(getActivity(), new Utils.TokenReceivedListener() {
                                @Override
                                public void onTokenReceived() {
                                    getProductList(Utils.token);
                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(), "Product List API Call Succesful but Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductListResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setProductListAdapter(ProductListResult productList) {
    productListRecyclerView.setAdapter( new ProductListAdapter(getActivity(), productList) );
    }

}
