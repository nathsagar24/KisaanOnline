package com.example.kisaanonline.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserCredentials {
    private String username,email,contact,address,state,city,pincode;

    public UserCredentials(String username, String email, String contact, String address, String state, String city, String pincode) {
        this.username = username;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
    }

    protected UserCredentials(Parcel in) {
        username = in.readString();
        email = in.readString();
        contact = in.readString();
        address = in.readString();
        state = in.readString();
        city = in.readString();
        pincode = in.readString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
