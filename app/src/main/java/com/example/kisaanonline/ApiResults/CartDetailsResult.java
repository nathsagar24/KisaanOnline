package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDetailsResult {
    @SerializedName("data")
    private List<Data> dataList;
    @SerializedName("total")
    private Total total;

    public CartDetailsResult(List<Data> dataList, Total total) {
        this.dataList = dataList;
        this.total = total;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public Total getTotal() {
        return total;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public class Total{
        @SerializedName("sub_total")
        private float subTotal;
        @SerializedName("gst_amt")
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

        public void setSubTotal(float subTotal) {
            this.subTotal = subTotal;
        }

        public void setTotalGstAmt(float totalGstAmt) {
            this.totalGstAmt = totalGstAmt;
        }

        public void setNetTotal(float netTotal) {
            this.netTotal = netTotal;
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
        @SerializedName("discount_available")
        private Integer  discount;
        @SerializedName("org_price")
        private float orgPrice;

        public Data(String productName, int cartDetailId, int qty, float price, float total, float gstPercent, float gstAmt, String imageUrl, String productId, String variantId, String availability, float availableQty, Integer discount, float orgPrice) {
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
            this.discount = discount;
            this.orgPrice = orgPrice;
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

        public Integer getDiscount() {
            return discount;
        }

        public float getOrgPrice() {
            return orgPrice;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setCartDetailId(int cartDetailId) {
            this.cartDetailId = cartDetailId;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void setTotal(float total) {
            this.total = total;
        }

        public void setGstPercent(float gstPercent) {
            this.gstPercent = gstPercent;
        }

        public void setGstAmt(float gstAmt) {
            this.gstAmt = gstAmt;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setVariantId(String variantId) {
            this.variantId = variantId;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public void setAvailableQty(float availableQty) {
            this.availableQty = availableQty;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public void setOrgPrice(float orgPrice) {
            this.orgPrice = orgPrice;
        }
    }
}
