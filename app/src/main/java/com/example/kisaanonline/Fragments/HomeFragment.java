package com.example.kisaanonline.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
        Log.v("ACTIVITY : ", "" + getActivity());
        Utils.refreshToken(getActivity());
        Log.v("TOKEN : ","" + Utils.token);
        getProductList(Utils.token);

        //Setting Up Search
        searchView = v.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String searchKeyword) {
                        hideSoftKeyboard(getActivity(), searchView.getWindowToken());
                        if(searchKeyword.isEmpty()) {Toast.makeText(getActivity(), "Please Enter Something!!",Toast.LENGTH_SHORT).show();return false;}
                        Utils.refreshToken(getActivity());
                        getSearchedProducts(searchKeyword, Utils.token);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                }
        );

        setPriceSeekbarListener();

        return v;
    }

    private void hideSoftKeyboard(Activity activity, IBinder windowToken){

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0);

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

   /* private void getSearchedProducts1(String searchKeyword) {

        Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        if(response.code() == 200) {
                            final String token = response.body().getToken();
                            getSearchedProducts2(searchKeyword, token);
                        }
                        else{
                            Toast.makeText(getActivity(), "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APITokenResult> call, Throwable t) {
                            Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
*/
    private void getSearchedProducts(String searchKeyword, String token) {
        Call<ProductListResult> callSearchedProductsList = Utils.getAPIInstance()
                .getSearchedProducts(new SearchCredentials(searchKeyword
                                , priceSeekBar.getSelectedMinValue().intValue()
                                , priceSeekBar.getSelectedMaxValue().intValue()
                                , 0)
                        ,"Bearer " + token);
        callSearchedProductsList.enqueue(
                new Callback<ProductListResult>() {
                    @Override
                    public void onResponse(Call<ProductListResult> call, Response<ProductListResult> response) {
                        if(response.code() == 200) {
                            setProductListAdapter(response.body());
                        }
                        else{
                            Toast.makeText(getActivity(), "API Call Succesful but Error: " + response.errorBody() , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductListResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    /*private void getProductList1(){
        Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        if(response.code() == 200) {
                            final String token = response.body().getToken();
                            getProductList2(token);
                        }
                        else{
                            Toast.makeText(getActivity(), "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APITokenResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }*/

    private void getProductList(String token) {
        Call<ProductListResult> callProductList = Utils.getAPIInstance()
                .getProductList(new ProductListBody(),
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
                        else{
                            Toast.makeText(getActivity(), "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
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
