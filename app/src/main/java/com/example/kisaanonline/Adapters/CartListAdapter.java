package com.example.kisaanonline.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kisaanonline.ApiResults.CartListResult;
import com.example.kisaanonline.R;
import com.squareup.picasso.Picasso;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartProductViewHolder> {

    private CartListResult cartListResult;
    private FragmentActivity context;

    public CartListAdapter(FragmentActivity context, CartListResult cartListResult){

        this.context = context;
        this.cartListResult = cartListResult;

    }

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView cartProductNameAndQty,cartProductPrice;
        public CartProductViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.cart_product_image);
            cartProductNameAndQty = itemView.findViewById(R.id.cart_product_name_and_qty);
            cartProductPrice = itemView.findViewById(R.id.cart_product_price);

        }
    }

    @NonNull
    @Override
    public CartProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_product_layout, parent, false);
        return new CartProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductViewHolder holder, int position) {
        holder.cartProductNameAndQty.setText("" + cartListResult.getCartList().get(position).getProductName() + " X " + cartListResult.getCartList().get(position).getQuantity());
        holder.cartProductPrice.setText("Rs: " + cartListResult.getCartList().get(position).getProductPrice());
        if(cartListResult.getCartList().get(position).getImageUrl()!=null) {
            Glide
                    .with(context)
                    .load("http://103.106.20.186:9009/shoppingcart_api/resources/files/Product_Files/" +
                            cartListResult.getCartList().get(position).getImageUrl().substring(cartListResult.getCartList().get(position).getImageUrl().lastIndexOf("\\") + 1))
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(holder.icon);
        }
    }

    @Override
    public int getItemCount() {
        return cartListResult.getCartList().size();
    }

}
