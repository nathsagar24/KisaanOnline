package com.example.kisaanonline;

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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartDetailsAdapter extends RecyclerView.Adapter<CartDetailsAdapter.CartDetailsViewHolder> {
    private List<CartDetails.Data> data;
    private FragmentActivity context;

    public CartDetailsAdapter(FragmentActivity context, List<CartDetails.Data> data) {
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
        Call<APIToken> callToken = Utils.getAPIInstance().getToken(new AuthenticationCredentials("efive", "efive123"));
        callToken.enqueue(
                new Callback<APIToken>() {
                    @Override
                    public void onResponse(Call<APIToken> call, Response<APIToken> response) {
                        final String token = response.body().getToken();

                        List<ProductCredentialsList.ProductCredentials> productCredentialsList = new ArrayList<>();
                        productCredentialsList.add(new ProductCredentialsList.ProductCredentials(productId, variantId, qty));
                        Call<CartProductSaveResult> callCartProductSave = Utils.getAPIInstance().saveCartProduct(
                                                        productCredentialsList,
                                                        "Bearer " + token,
                                                        Utils.userId
                                                        );
                        callCartProductSave.enqueue(
                                new Callback<CartProductSaveResult>() {
                                    @Override
                                    public void onResponse(Call<CartProductSaveResult> call, Response<CartProductSaveResult> response) {
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
                                    public void onFailure(Call<CartProductSaveResult> call, Throwable t) {
                                        Toast.makeText(context, "API Call For Saving Cart Item Failed : " + t.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Call<APIToken> call, Throwable t) {
                        Toast.makeText(context, "API Call for getting token failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}
