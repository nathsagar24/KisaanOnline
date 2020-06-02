package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class CartSaveResult {
    @SerializedName("iserror")
    private String isError;
    @SerializedName("errorString")
    private String errorString;
    @SerializedName("errorTitle")
    private String errorTitle;
    @SerializedName("errorType")
    private String errorType;

    public CartSaveResult(String isError, String errorString, String errorTitle, String errorType) {
        this.isError = isError;
        this.errorString = errorString;
        this.errorTitle = errorTitle;
        this.errorType = errorType;
    }

    public String getErrorString() {
        return errorString;
    }

    public String getIsError() {
        return isError;
    }

    public String getErrorTitle() { return errorTitle; }

    public String getErrorType() { return errorType; }
}
