package com.example.kisaanonline.Adapters;

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
import com.example.kisaanonline.ApiResults.CartListResult;
import com.example.kisaanonline.ApiResults.RemoveFromCartResult;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartProductViewHolder> {

    private CartListResult cartListResult;
    private FragmentActivity context;

    public CartListAdapter(FragmentActivity context, CartListResult cartListResult){

        this.context = context;
        this.cartListResult = cartListResult;

    }

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage, removeBtn;
        private TextView productNameAndQty,productPrice;
        public CartProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cart_product_image);
            productNameAndQty = itemView.findViewById(R.id.cart_product_name_and_qty);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            removeBtn = itemView.findViewById(R.id.remove_btn);
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
                    .placeholder(R.mipmap.image_loading)
                    .into(holder.productImage);
        holder.removeBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Remove Button Clicked", Toast.LENGTH_SHORT).show();
                        removeFromCart(cartListResult.getCartList().get(position).getCartId(), Utils.token, Utils.userId);
                    }
                }
        );
    }

    //Gets deleted with cart_id not product_id
    private void removeFromCart(String cartId, String token, String userId) {

        Call<RemoveFromCartResult> callRemove = Utils.getAPIInstance().removeFromCart(cartId, "Bearer " + token, userId);
        callRemove.enqueue(
                new Callback<RemoveFromCartResult>() {
                    @Override
                    public void onResponse(Call<RemoveFromCartResult> call, Response<RemoveFromCartResult> response) {
                        if(response.code() == 200) {
                            if (response.body().getIsError().equals("N")) {
                                //Not working can't update without resetting the adapter
                                notifyDataSetChanged();
                                Toast.makeText(context, "Product Successfully removed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Please give correct credentials!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (response.code() == 401 || response.code() == 500){
                            Utils.refreshToken(context, new Utils.TokenReceivedListener() {
                                @Override
                                public void onTokenReceived() {

                                    removeFromCart(cartId, Utils.token, Utils.userId);
                                    Toast.makeText(context, "Refreshing...." + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(context, "API Call Succesful but Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RemoveFromCartResult> call, Throwable t) {
                        Toast.makeText(context, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @Override
    public int getItemCount() {
        return cartListResult.getCartList().size();
    }

}
