package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class CartSaveResult {
    @SerializedName("iserror")
    private String isError;
    @SerializedName("errorString")
    private String errorString;

    public CartSaveResult(String isError, String errorString) {
        this.isError = isError;
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }
}
