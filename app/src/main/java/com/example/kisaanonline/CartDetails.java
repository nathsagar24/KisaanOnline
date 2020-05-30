package com.example.kisaanonline;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDetails {
    @SerializedName("data")
    private List<Data> dataList;
    @SerializedName("total")
    private List<Total> totalList;

    public CartDetails(List<Data> dataList, List<Total> totalList) {
        this.dataList = dataList;
        this.totalList = totalList;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public List<Total> getTotalList() {
        return totalList;
    }

    public void setTotalList(List<Total> totalList) {
        this.totalList = totalList;
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

        public void setSubTotal(float subTotal) {
            this.subTotal = subTotal;
        }

        public float getTotalGstAmt() {
            return totalGstAmt;
        }

        public void setTotalGstAmt(float totalGstAmt) {
            this.totalGstAmt = totalGstAmt;
        }

        public float getNetTotal() {
            return netTotal;
        }

        public void setNetTotal(float netTotal) {
            this.netTotal = netTotal;
        }
    }


    public static class Data{
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

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getCartDetailId() {
            return cartDetailId;
        }

        public void setCartDetailId(int cartDetailId) {
            this.cartDetailId = cartDetailId;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }

        public float getGstPercent() {
            return gstPercent;
        }

        public void setGstPercent(float gstPercent) {
            this.gstPercent = gstPercent;
        }

        public float getGstAmt() {
            return gstAmt;
        }

        public void setGstAmt(float gstAmt) {
            this.gstAmt = gstAmt;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getVariantId() {
            return variantId;
        }

        public void setVariantId(String variantId) {
            this.variantId = variantId;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public float getAvailableQty() {
            return availableQty;
        }

        public void setAvailableQty(float availableQty) {
            this.availableQty = availableQty;
        }

    }
}
