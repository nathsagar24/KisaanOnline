package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class APITokenResult {
    @SerializedName("token")
    private String token;

    public APITokenResult(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
