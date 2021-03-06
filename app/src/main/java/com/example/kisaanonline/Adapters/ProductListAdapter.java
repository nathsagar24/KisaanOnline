package com.example.kisaanonline.Adapters;

import android.animation.LayoutTransition;
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

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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
            ((ViewGroup)v.findViewById(R.id.product_img_root)).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            return new ProductViewHolder(v);
        }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.productName.setText(productListResult.getData().get(position).getProductName());
        holder.productPrice.setText("Rs. : " + productListResult.getData().get(position).getPrice());
            Glide
                    .with(context)
                    .load(productListResult.getData().get(position).getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.mipmap.image_loading)
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
                            addNewProductToCart(productListResult.getData().get(position).getProductId(),
                                            productListResult.getData().get(position).getVariantId(), 1, Utils.token);
                        }
                        }
                    }
        );

    }

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
                                Toast.makeText(context, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
                                //Utils.setFragment(context, new CartDetailsFragment(), true);
                            } else {
                                Toast.makeText(context, "Please give correct credentials!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (response.code() == 401 || response.code() == 500){
                            Utils.refreshToken(context, new Utils.TokenReceivedListener() {
                                @Override
                                public void onTokenReceived() {
                                    addNewProductToCart(productId, variantId, qty, Utils.token);
                                }
                            });
                        }
                        else{
                            Toast.makeText(context, "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartSaveResult> call, Throwable t) {
                        Toast.makeText(context, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if(!Utils.isNetworkConnected(context)) {
                            Toast.makeText(context, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
                        }
                        addNewProductToCart(productId, variantId, qty, Utils.token);
                    }
                }
        );
    }

    @Override
        public int getItemCount() {
            return productListResult.getData().size();
        }
}
