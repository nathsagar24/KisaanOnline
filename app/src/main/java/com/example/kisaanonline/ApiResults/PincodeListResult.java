package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PincodeListResult {
    @SerializedName("data")
    List<PincodeDetails> data;

    public PincodeListResult(List<PincodeDetails> data) {
        this.data = data;
    }

    public List<PincodeDetails> getData() {
        return data;
    }

    public class PincodeDetails{

        @SerializedName("name")
        private String name;

        @SerializedName("pincode")
        private String pincode;

        public PincodeDetails(String name, String pincode) {
            this.name = name;
            this.pincode = pincode;
        }

        public String getName() {
            return name;
        }

        public String getPincode() {
            return pincode;
        }

    }

}
