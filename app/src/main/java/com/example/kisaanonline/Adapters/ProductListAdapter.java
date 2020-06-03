package com.example.kisaanonline.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.ApiResults.CartSaveResult;
import com.example.kisaanonline.ApiResults.ProductListResult;
import com.example.kisaanonline.Fragments.CartDetailsFragment;
import com.example.kisaanonline.Fragments.LoginFragment;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.Models.ProductCredentials;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private ProductListResult productListResult;
    private FragmentActivity context;

    public ProductListAdapter(FragmentActivity context, ProductListResult productListResult) {
        this.context = context;
        this.productListResult = productListResult;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productName,productPrice,addToCartBtn;
        private ImageView productImage;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            addToCartBtn = itemView.findViewById(R.id.add_to_cart_btn);
        }
    }
        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
            return new ProductViewHolder(v);
        }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.productName.setText(productListResult.getData().get(position).getProductName());
        holder.productPrice.setText("Rs. : " + productListResult.getData().get(position).getPrice());
            Glide
                    .with(context)
                    .load(productListResult.getData().get(position).getImageUrl())
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(holder.productImage);
            holder.productImage.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.addToCartBtn.setVisibility(View.VISIBLE);
                        }
                    }
            );
            holder.addToCartBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!Utils.loggedIn) Utils.setFragment(context, new LoginFragment(), true);
                        else {
                            Utils.refreshToken(context);
                            addNewProductToCart(productListResult.getData().get(position).getProductId(),
                                    productListResult.getData().get(position).getVariantId(), 1, Utils.token);
                        }
                        }
                    }
        );

    }

    /*private void addNewProductToCart(String productId, String variantId,int qty) {
        Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        String token = response.body().getToken();
                        List<ProductCredentials> productCredentialsList =new ArrayList<>();
                        productCredentialsList.add(new ProductCredentials(productId, variantId, qty));
                        Call<CartSaveResult> callCartProductSaveResult = Utils.getAPIInstance().saveCartProduct(
                                                                                productCredentialsList,
                                                                                "Bearer " + token,
                                                                                Utils.userId
                                                                                );
                        Log.v("User Id", Utils.userId);
                        callCartProductSaveResult.enqueue(
                                new Callback<CartSaveResult>() {
                                    @Override
                                    public void onResponse(Call<CartSaveResult> call, Response<CartSaveResult> response) {
                                        Log.v("RESPONSE : ","" + response.errorBody());
                                        if(response.body().getIsError().equals("N")){
                                            Toast.makeText(context, "Product Added To Cart", Toast.LENGTH_SHORT).show();
                                            Utils.setFragment(context, new CartDetailsFragment(), true);
                                        }
                                        else{
                                            Toast.makeText(context, "Error Occured! Product Not Added...Try Again", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CartSaveResult> call, Throwable t) {
                                            Toast.makeText(context,"Add To Cart API Call Failed : " + t.getMessage(), Toast.LENGTH_SHORT);
                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Call<APITokenResult> call, Throwable t) {

                    }
                }
        );
    }*/

    private void addNewProductToCart(String productId, String variantId,int qty, String token) {
        List<ProductCredentials> productCredentialsList =new ArrayList<>();
        productCredentialsList.add(new ProductCredentials(productId, variantId, qty));
        Call<CartSaveResult> callCartProductSave = Utils.getAPIInstance().saveCartProduct(
                productCredentialsList,
                "Bearer " + token,
                Utils.userId
        );
        callCartProductSave.enqueue(
                new Callback<CartSaveResult>() {
                    @Override
                    public void onResponse(Call<CartSaveResult> call, Response<CartSaveResult> response) {
                        if(response.code() == 200) {
                            if (response.body().getIsError().equals("N")) {
                                Utils.setFragment(context, new CartDetailsFragment(), true);
                            } else {
                                Toast.makeText(context, "Please give correct credentials!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(context, "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartSaveResult> call, Throwable t) {
                        Toast.makeText(context, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
        public int getItemCount() {
            return productListResult.getData().size();
        }
}
