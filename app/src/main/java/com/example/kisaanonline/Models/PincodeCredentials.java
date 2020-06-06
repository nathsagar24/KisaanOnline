package com.example.kisaanonline.Models;

public class PincodeCredentials {

    private String state_name, city_name;

    public PincodeCredentials(String state_name, String city_name) {
        this.state_name = state_name;
        this.city_name = city_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

}
