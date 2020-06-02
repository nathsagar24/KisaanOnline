package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListResult {
    @SerializedName("data")
    private List<CartProduct> cartList;
    @SerializedName("totalprice")
    private List<TotalPrice> totalPriceList;

    public CartListResult(List<CartProduct> cartList, List<TotalPrice> totalPriceList) {
        this.cartList = cartList;
        this.totalPriceList = totalPriceList;
    }

    public List<CartProduct> getCartList() {
        return cartList;
    }

    public List<TotalPrice> getTotalPriceList() {
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
    }

    public class TotalPrice{
        @SerializedName("sum(price*quantity)")
        private float totalPrice;

        public TotalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
        }

        public float getTotalPrice() {
            return totalPrice;
        }
    }
}
