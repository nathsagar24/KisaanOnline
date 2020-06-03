package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class APITokenResult {
    @SerializedName("token")
    private String token;
    @SerializedName("expiry")
    private String expiry;

    public APITokenResult(String token, String expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public String getExpiry() { return expiry; }
}
