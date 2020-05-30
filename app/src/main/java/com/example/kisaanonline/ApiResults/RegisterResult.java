package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class RegisterResult {
    @SerializedName("iserror")
    private String isError;

    public RegisterResult(String isError) {
        this.isError = isError;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }
}
