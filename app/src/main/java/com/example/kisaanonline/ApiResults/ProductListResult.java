package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListResult {
    @SerializedName("data")
    private List<ProductDetails> data;
    @SerializedName("iserror")
    private String isError;

    public ProductListResult(List<ProductDetails> data, String isError) {
        this.data = data;
        this.isError = isError;
    }

    public List<ProductDetails> getData() {
        return data;
    }

    public String getIsError() { return isError; }

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

        public String getPrice() {
            return price;
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

    }

}
