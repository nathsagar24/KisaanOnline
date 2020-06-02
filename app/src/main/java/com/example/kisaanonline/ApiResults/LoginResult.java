package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("userid")
    private String userId;
    @SerializedName("iserror")
    private String isError;
    @SerializedName("errorString")
    private String errorString;
    @SerializedName("errorTitle")
    private String errorTitle;

    public LoginResult(String userId, String isError, String errorString, String errorTitle) {
        this.userId = userId;
        this.isError = isError;
        this.errorString = errorString;
        this.errorTitle = errorTitle;
    }

    public String getUserId() {
        return userId;
    }

    public String getIsError() {
        return isError;
    }

    public String getErrorString() { return errorString; }

    public String getErrorTitle() { return errorTitle; }
}
