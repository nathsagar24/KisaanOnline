package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("userid")
    private String userId;
    @SerializedName("iserror")
    private String isError;

    public LoginResult(String userId, String isError) {
        this.userId = userId;
        this.isError = isError;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }
}
