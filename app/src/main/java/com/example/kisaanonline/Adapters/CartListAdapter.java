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
import com.example.kisaanonline.ApiResults.CartListResult;
import com.example.kisaanonline.R;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartProductViewHolder> {

    private CartListResult cartListResult;
    private FragmentActivity context;

    public CartListAdapter(FragmentActivity context, CartListResult cartListResult){

        this.context = context;
        this.cartListResult = cartListResult;

    }

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productNameAndQty,productPrice;
        public CartProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cart_product_image);
            productNameAndQty = itemView.findViewById(R.id.cart_product_name_and_qty);
            productPrice = itemView.findViewById(R.id.cart_product_price);

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
        holder.productNameAndQty.setText("" + cartListResult.getCartList().get(position).getProductName() + " X " + cartListResult.getCartList().get(position).getQuantity());
        holder.productPrice.setText("Rs: " + cartListResult.getCartList().get(position).getProductPrice());
            Glide
                    .with(context)
                    .load(cartListResult.getCartList().get(position).getImageUrl())
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return cartListResult.getCartList().size();
    }

}
