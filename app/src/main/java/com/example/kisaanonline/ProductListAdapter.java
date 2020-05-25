package com.example.kisaanonline;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kisaanonline.Fragments.CartDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

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
                        sendProductCredentials(productDetailsList.get(position).getProductId(), productDetailsList.get(position).getVariantId());
                    }
                }
        );
        }

    }

    private void sendProductCredentials(String productId, String variantId) {
        Bundle args=new Bundle();
        args.putString("productid", productId);
        args.putString("variantid", variantId);
        Fragment newFragment =new CartDetailsFragment();
        newFragment.setArguments(args);
        Utils.setFragment(context, newFragment, true);
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
