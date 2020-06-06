package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityListResult {
    @SerializedName("data")
    List<CityDetails> data;

    public CityListResult(List<CityDetails> data) {
        this.data = data;
    }

    public List<CityDetails> getData() {
        return data;
    }

    public class CityDetails{

        @SerializedName("city")
        String city;

        public CityDetails(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

    }
}
