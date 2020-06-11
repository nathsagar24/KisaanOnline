package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListResult {
    @SerializedName("data")
    private List<CartProduct> cartList;
    @SerializedName("totalprice")
    private Integer totalPriceList;

    public CartListResult(List<CartProduct> cartList, Integer totalPriceList) {
        this.cartList = cartList;
        this.totalPriceList = totalPriceList;
    }

    public List<CartProduct> getCartList() {
        return cartList;
    }

    public Integer getTotalPrice() {
        return totalPriceList;
    }

    public class CartProduct{
        @SerializedName("productname")
        private String productName;
        @SerializedName("image")
        private String imageUrl;
        @SerializedName("quantity")
        private Integer quantity;
        @SerializedName("productprice")
        private float productPrice;
        @SerializedName("discount_available")
        private Integer discount;
        @SerializedName("productid")
        private String productId;
        @SerializedName("variantid")
        private String variantId;
        @SerializedName("cart_id")
        private String cartId;

        public CartProduct(String productName, String imageUrl, int quantity, float productPrice, Integer discount, String productId, String variantId, String cartId) {
            this.productName = productName;
            this.imageUrl = imageUrl;
            this.quantity = quantity;
            this.productPrice = productPrice;
            this.discount = discount;
            this.productId = productId;
            this.variantId = variantId;
            this.cartId = cartId;
        }

        public String getProductName() {
            return productName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public int getQuantity() {
            return quantity;
        }

        public float getProductPrice() {
            return productPrice;
        }

        public String getProductId() {
            return productId;
        }

        public String getVariantId() {
            return variantId;
        }

        public Integer getDiscount() {
            return discount;
        }

        public String getCartId() {
            return cartId;
        }
    }

}
