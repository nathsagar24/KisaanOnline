package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListResult {
    @SerializedName("data")
    private List<CartProduct> cartProductsList;
    @SerializedName("totalprice")
    private List<TotalPrice> totalPriceList;

    public CartListResult(List<CartProduct> cartProductsList, List<TotalPrice> totalPriceList) {
        this.cartProductsList = cartProductsList;
        this.totalPriceList = totalPriceList;
    }

    public List<CartProduct> getCartProductsList() {
        return cartProductsList;
    }

    public void setCartProductsList(List<CartProduct> cartProductsList) {
        this.cartProductsList = cartProductsList;
    }

    public List<TotalPrice> getTotalPriceList() {
        return totalPriceList;
    }

    public void setTotalPriceList(List<TotalPrice> totalPriceList) {
        this.totalPriceList = totalPriceList;
    }

    public static class CartProduct{
        @SerializedName("productname")
        private String productName;
        @SerializedName("image")
        private String imageUrl;
        @SerializedName("quantity")
        private int quantity;
        @SerializedName("productprice")
        private float productPrice;
        @SerializedName("productid")
        private String productId;
        @SerializedName("variantid")
        private String variantId;

        public CartProduct(String productName, String imageUrl, int quantity, float productPrice, String productId, String variantId) {
            this.productName = productName;
            this.imageUrl = imageUrl;
            this.quantity = quantity;
            this.productPrice = productPrice;
            this.productId = productId;
            this.variantId = variantId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public float getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(float productPrice) {
            this.productPrice = productPrice;
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
    }

    public static class TotalPrice{
        @SerializedName("sum(price*quantity)")
        private float totalPrice;

        public TotalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
        }

        public float getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
}
