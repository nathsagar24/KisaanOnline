package com.example.kisaanonline;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsList {
    @SerializedName("data")
    List<ProductDetails> data;

    public ProductDetailsList(List<ProductDetails> data) {
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
        String productName;

        @SerializedName("price")
        String price;

        @SerializedName("image")
        String imageUrl;

        @SerializedName("productid")
        String productId;

        @SerializedName("variantid")
        String variantId;

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
