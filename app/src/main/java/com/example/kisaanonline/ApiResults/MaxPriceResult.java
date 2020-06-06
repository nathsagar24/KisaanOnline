package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class MaxPriceResult {
    @SerializedName("max_price")
    Integer maxPrice;

    public MaxPriceResult(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }
}
