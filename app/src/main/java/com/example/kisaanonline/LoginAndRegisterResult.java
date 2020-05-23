package com.example.kisaanonline;

import com.google.gson.annotations.SerializedName;

public class LoginAndRegisterResult {
    @SerializedName("iserror")
    private String isError;

    public LoginAndRegisterResult(String isError) {
        this.isError = isError;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }
}
