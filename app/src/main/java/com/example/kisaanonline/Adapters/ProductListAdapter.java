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

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.ApiResults.CartSaveResult;
import com.example.kisaanonline.ApiResults.ProductListResult;
import com.example.kisaanonline.Fragments.CartDetailsFragment;
import com.example.kisaanonline.Fragments.LoginFragment;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.Models.ProductCredentialsList;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private List<ProductListResult.ProductDetails> productDetailsList;
    private FragmentActivity context;

    public ProductListAdapter(FragmentActivity context, List<ProductListResult.ProductDetails> productDetailsList) {
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
        Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive","efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        String token = response.body().getToken();
                        List<ProductCredentialsList.ProductCredentials> productCredentialsList =new ArrayList<>();
                        productCredentialsList.add(new ProductCredentialsList.ProductCredentials(productId, variantId, qty));
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
