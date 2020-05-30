package com.example.kisaanonline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class CartProductListAdapter extends RecyclerView.Adapter<CartProductListAdapter.CartProductViewHolder> {

    private CartProducts cartProducts;
    private FragmentActivity context;

    public CartProductListAdapter(FragmentActivity context, CartProducts cartProducts){

        this.context = context;
        this.cartProducts = cartProducts;

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
        holder.cartProductNameAndQty.setText("" + cartProducts.getCartProductsList().get(position).getProductName() + " X " + cartProducts.getCartProductsList().get(position).getQuantity());
        holder.cartProductPrice.setText("Rs: " + cartProducts.getCartProductsList().get(position).getProductPrice());
        if(cartProducts.getCartProductsList().get(position).getImageUrl()!=null) {
            Picasso
                    .with(context)
                    .load("http://103.106.20.186:9009/shoppingcart_api/resources/files/Product_Files/" +
                            cartProducts.getCartProductsList().get(position).getImageUrl().substring(cartProducts.getCartProductsList().get(position).getImageUrl().lastIndexOf("\\") + 1))
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(holder.icon);
        }
    }

    @Override
    public int getItemCount() {
        return cartProducts.getCartProductsList().size();
    }

}
