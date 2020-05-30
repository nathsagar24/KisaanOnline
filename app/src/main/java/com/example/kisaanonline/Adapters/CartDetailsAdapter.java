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

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.ApiResults.CartDetailsResult;
import com.example.kisaanonline.ApiResults.CartSaveResult;
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

public class CartDetailsAdapter extends RecyclerView.Adapter<CartDetailsAdapter.CartDetailsViewHolder> {
    private List<CartDetailsResult.Data> data;
    private FragmentActivity context;

    public CartDetailsAdapter(FragmentActivity context, List<CartDetailsResult.Data> data) {
        this.context = context;
        this.data = data;
    }

    public class CartDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView productName,price,total,gstPecent,gstAmt;
        private ImageView productImage;
        private NumberPicker qtyPicker;
        public CartDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            total = itemView.findViewById(R.id.total_product_price);
            gstPecent = itemView.findViewById(R.id.gst_percent);
            gstAmt =itemView.findViewById(R.id.gst_amt);
            productImage = itemView.findViewById(R.id.product_image);
            qtyPicker = itemView.findViewById(R.id.quantity_picker);
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
        Log.v("POSITION : ","" + position);
        holder.productName.setText(data.get(position).getProductName());
        holder.price.setText("Rs. " + data.get(position).getPrice());
        holder.total.setText("Rs. " + data.get(position).getTotal());
        holder.gstPecent.setText("" + data.get(position).getGstPercent() + " %");
        holder.gstAmt.setText("Rs. " + data.get(position).getGstAmt());
        holder.qtyPicker.setMaxValue((int)data.get(position).getAvailableQty());
        holder.qtyPicker.setMinValue(1);
        holder.qtyPicker.setValue(data.get(position).getQty());
        holder.qtyPicker.setOnValueChangedListener(
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                        //holder.qtyPicker.setValue(newVal);
                        saveProductToCart(data.get(position).getProductId(),data.get(position).getVariantId(),newVal);
                    }
                }
        );
        if(data.get(position).getImageUrl()!=null){
            Picasso
                    .with(context)
                    .load("http://103.106.20.186:9009/shoppingcart_api/resources/files/Product_Files/" +
                            data.get(position).getImageUrl().substring(data.get(position).getImageUrl().lastIndexOf("\\") + 1))
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(holder.productImage);
        }
    }

    @Override
    public int getItemCount() {
        Log.v("ITEM COUNT : ", "" + data.size());
        return data.size();
    }

    private void saveProductToCart(String productId, String variantId, int qty){
        Call<APITokenResult> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive", "efive123"));
        callToken.enqueue(
                new Callback<APITokenResult>() {
                    @Override
                    public void onResponse(Call<APITokenResult> call, Response<APITokenResult> response) {
                        final String token = response.body().getToken();

                        List<ProductCredentialsList.ProductCredentials> productCredentialsList = new ArrayList<>();
                        productCredentialsList.add(new ProductCredentialsList.ProductCredentials(productId, variantId, qty));
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
                                                Toast.makeText(context, "Quantity Updated!!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "Quantity didn't get saved!! : " + response.body().getErrorString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(context, "Response Received but Error : " +response.errorBody(),Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CartSaveResult> call, Throwable t) {
                                        Toast.makeText(context, "API Call For Saving Cart Item Failed : " + t.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Call<APITokenResult> call, Throwable t) {
                        Toast.makeText(context, "API Call for getting token failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}
