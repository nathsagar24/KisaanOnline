package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDetailsResult {
    @SerializedName("data")
    private List<Data> dataList;
    @SerializedName("total")
    private List<Total> totalList;

    public CartDetailsResult(List<Data> dataList, List<Total> totalList) {
        this.dataList = dataList;
        this.totalList = totalList;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public List<Total> getTotalList() {
        return totalList;
    }

    public static class Total{
        @SerializedName("sub_total")
        private float subTotal;
        @SerializedName("total_gst_amt")
        private float totalGstAmt;
        @SerializedName("net_total")
        private float netTotal;

        public Total(float subTotal, float totalGstAmt, float netTotal) {
            this.subTotal = subTotal;
            this.totalGstAmt = totalGstAmt;
            this.netTotal = netTotal;
        }

        public float getSubTotal() {
            return subTotal;
        }

        public float getTotalGstAmt() {
            return totalGstAmt;
        }

        public float getNetTotal() {
            return netTotal;
        }

    }


    public class Data{
        @SerializedName("product_name")
        private String productName;
        @SerializedName("cart_detail_id")
        private int cartDetailId;
        @SerializedName("quantity")
        private int qty;
        @SerializedName("price")
        private float price;
        @SerializedName("total")
        private float total;
        @SerializedName("tax_per")
        private float gstPercent;
        @SerializedName("gst_amt")
        private float gstAmt;
        @SerializedName("image")
        private String imageUrl;
        @SerializedName("product_id")
        private String productId;
        @SerializedName("varient_id")
        private String variantId;
        @SerializedName("available")
        private String availability;
        @SerializedName("available_qty")
        private float availableQty;

        public Data(String productName, int cartDetailId, int qty, float price, float total, float gstPercent, float gstAmt, String imageUrl, String productId, String variantId, String availability, float availableQty) {
            this.productName = productName;
            this.cartDetailId = cartDetailId;
            this.qty = qty;
            this.price = price;
            this.total = total;
            this.gstPercent = gstPercent;
            this.gstAmt = gstAmt;
            this.imageUrl = imageUrl;
            this.productId = productId;
            this.variantId = variantId;
            this.availability = availability;
            this.availableQty = availableQty;
        }

        public String getProductName() {
            return productName;
        }

        public int getCartDetailId() {
            return cartDetailId;
        }

        public int getQty() {
            return qty;
        }

        public float getPrice() {
            return price;
        }

        public float getTotal() {
            return total;
        }

        public float getGstPercent() {
            return gstPercent;
        }

        public float getGstAmt() {
            return gstAmt;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getProductId() {
            return productId;
        }

        public String getVariantId() {
            return variantId;
        }

        public String getAvailability() {
            return availability;
        }

        public float getAvailableQty() {
            return availableQty;
        }

    }
}
