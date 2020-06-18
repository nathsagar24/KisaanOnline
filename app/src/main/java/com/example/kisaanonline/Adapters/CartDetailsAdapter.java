package com.example.kisaanonline.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.ApiResults.CartDetailsResult;
import com.example.kisaanonline.ApiResults.CartSaveResult;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.Models.ProductCredentials;
import com.example.kisaanonline.R;
import com.example.kisaanonline.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartDetailsAdapter extends RecyclerView.Adapter<CartDetailsAdapter.CartDetailsViewHolder> {
    private CartDetailsResult cartDetailsResult;
    private FragmentActivity context;

    public CartDetailsAdapter(FragmentActivity context, CartDetailsResult cartDetailsResult) {
        this.context = context;
        this.cartDetailsResult = cartDetailsResult;
        Utils.checkoutCartDetails = cartDetailsResult;
    }

    public class CartDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView productName,price,total, gstPercent, gstAmt, dicount;
        private ImageView productImage, deleteBtn;
        private NumberPicker qtyPicker;
        public CartDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            dicount = itemView.findViewById(R.id.discount);
            total = itemView.findViewById(R.id.total_product_price);
            gstPercent = itemView.findViewById(R.id.gst_percent);
            gstAmt =itemView.findViewById(R.id.gst_amt);
            productImage = itemView.findViewById(R.id.product_image);
            qtyPicker = itemView.findViewById(R.id.quantity_picker);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }

    @NonNull
    @Override
    public CartDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_product_details_layout, parent, false);
        return new CartDetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartDetailsViewHolder holder, int position) {
        holder.productName.setText(cartDetailsResult.getDataList().get(position).getProductName());
        holder.price.setText("Rs. " + cartDetailsResult.getDataList().get(position).getPrice());
        holder.dicount.setText("Rs. " + cartDetailsResult.getDataList().get(position).getDiscount());
        holder.total.setText("Rs. " + cartDetailsResult.getDataList().get(position).getTotal());
        holder.gstPercent.setText("" + cartDetailsResult.getDataList().get(position).getGstPercent() + " %");
        holder.gstAmt.setText("Rs. " + cartDetailsResult.getDataList().get(position).getGstAmt());
        //holder.qtyPicker.setMaxValue((int) cartDetailsResult.getDataList().get(position).getAvailableQty());
        holder.qtyPicker.setMinValue(Math.min(1, (int) cartDetailsResult.getDataList().get(position).getAvailableQty()));
        holder.qtyPicker.setValue(cartDetailsResult.getDataList().get(position).getQty());
        holder.deleteBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        holder.itemView.setVisibility(View.GONE);
                        Utils.checkoutCartDetails.getTotal().setSubTotal(Utils.checkoutCartDetails.getTotal().getSubTotal() - Utils.checkoutCartDetails.getDataList().get(position).getTotal());
                        Utils.checkoutCartDetails.getTotal().setTotalGstAmt(Utils.checkoutCartDetails.getTotal().getTotalGstAmt() - Utils.checkoutCartDetails.getDataList().get(position).getGstAmt());
                        Utils.checkoutCartDetails.getTotal().setNetTotal(Utils.checkoutCartDetails.getTotal().getNetTotal() - Utils.checkoutCartDetails.getDataList().get(position).getTotal());
                        Utils.checkoutCartDetails.getDataList().remove(position);
                    }
                }
        );

        holder.qtyPicker.setOnValueChangedListener(
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                        Utils.checkoutCartDetails.getDataList().get(position).setQty(newVal);
                    }
                }
        );
            Glide
                    .with(context)
                    .load(cartDetailsResult.getDataList().get(position).getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.mipmap.image_loading)
                    .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return cartDetailsResult.getDataList().size();
    }

}
