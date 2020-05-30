package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListResult {
    @SerializedName("data")
    private List<ProductDetails> data;

    public ProductListResult(List<ProductDetails> data) {
        this.data = data;
    }

    public List<ProductDetails> getData() {
        return data;
    }

    public void setData(List<ProductDetails> data) {
        this.data = data;
    }

    public class ProductDetails{

        @SerializedName("product_name")
        private String productName;

        @SerializedName("price")
        private String price;

        @SerializedName("image")
        private String imageUrl;

        @SerializedName("productid")
        private String productId;

        @SerializedName("variantid")
        private String variantId;

        public ProductDetails(String productName, String price, String imageUrl, String productId, String variantId) {
            this.productName = productName;
            this.price = price;
            this.imageUrl = imageUrl;
            this.productId = productId;
            this.variantId = variantId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

    }

}
