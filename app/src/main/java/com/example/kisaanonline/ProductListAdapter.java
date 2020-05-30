package com.example.kisaanonline;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kisaanonline.Fragments.BillingDetailsFragment;
import com.example.kisaanonline.Fragments.CartDetailsFragment;
import com.example.kisaanonline.Fragments.LoginFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private List<ProductDetailsList.ProductDetails> productDetailsList;
    private FragmentActivity context;

    public ProductListAdapter(FragmentActivity context, List<ProductDetailsList.ProductDetails> productDetailsList) {
        this.context = context;
        this.productDetailsList = productDetailsList;
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
        holder.productName.setText(productDetailsList.get(position).getProductName());
        holder.productPrice.setText("Rs. : " + productDetailsList.get(position).getPrice());
        if(productDetailsList.get(position).getImageUrl()!=null) {
            Picasso
                    .with(context)
                    .load("http://103.106.20.186:9009/shoppingcart_api/resources/files/Product_Files/" +
                            productDetailsList.get(position).getImageUrl().substring(productDetailsList.get(position).getImageUrl().lastIndexOf("\\") + 1))
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(holder.productImage);
        }
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
                        if(!Utils.loggedIn) Utils.setFragment(context, new LoginFragment(), false);
                        else addNewProductToCart(productDetailsList.get(position).getProductId(), productDetailsList.get(position).getVariantId(), 1);
                    }
                }
        );

    }

    private void addNewProductToCart(String productId, String variantId,int qty) {
        Call<APIToken> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(
                new Callback<APIToken>() {
                    @Override
                    public void onResponse(Call<APIToken> call, Response<APIToken> response) {
                        String token = response.body().getToken();
                        List<ProductCredentialsList.ProductCredentials> productCredentialsList =new ArrayList<>();
                        productCredentialsList.add(new ProductCredentialsList.ProductCredentials(productId, variantId, qty));
                        Call<CartProductSaveResult> callCartProductSaveResult = Utils.getAPIInstance().saveCartProduct(
                                                                                productCredentialsList,
                                                                                "Bearer " + token,
                                                                                Utils.userId
                                                                                );
                        Log.v("User Id", Utils.userId);
                        callCartProductSaveResult.enqueue(
                                new Callback<CartProductSaveResult>() {
                                    @Override
                                    public void onResponse(Call<CartProductSaveResult> call, Response<CartProductSaveResult> response) {
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
                                    public void onFailure(Call<CartProductSaveResult> call, Throwable t) {
                                            Toast.makeText(context,"Add To Cart API Call Failed : " + t.getMessage(), Toast.LENGTH_SHORT);
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

    @Override
        public int getItemCount() {
            return productDetailsList.size();
        }

    @Override
    public void onViewDetachedFromWindow(@NonNull ProductViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
       // holder.addToCartBtn.setVisibility(View.GONE);
    }
}
