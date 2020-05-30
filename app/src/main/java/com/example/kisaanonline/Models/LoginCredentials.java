package com.example.kisaanonline.Models;

public class LoginCredentials {
    private String loginusername;
    private String loginpassword;

    public LoginCredentials(String loginusername, String loginpassword) {
        this.loginusername = loginusername;
        this.loginpassword = loginpassword;
    }

    public String getLoginusername() {
        return loginusername;
    }

    public void setLoginusername(String loginusername) {
        this.loginusername = loginusername;
    }

    public String getLoginpassword() {
        return loginpassword;
    }

    public void setLoginpassword(String loginpassword) {
        this.loginpassword = loginpassword;
    }
}
