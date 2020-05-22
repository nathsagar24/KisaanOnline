package com.example.kisaanonline;

import com.google.gson.annotations.SerializedName;

public class APIToken {
    @SerializedName("token")
    private String token;

    public APIToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
