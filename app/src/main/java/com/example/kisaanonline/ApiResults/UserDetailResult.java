package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetailResult {
    @SerializedName("userdetail")
    List<User> userDetail;

    public UserDetailResult(List<User> userDetail) {
        this.userDetail = userDetail;
    }

    public List<User> getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(List<User> userDetail) {
        this.userDetail = userDetail;
    }

    public class User{
      @SerializedName("name")
      private String name;
      @SerializedName("contact")
        private String contact;
      @SerializedName("email")
      private String email;
      @SerializedName("address")
      private String address;
      @SerializedName("state")
      private String state;
      @SerializedName("city")
      private String city;
      @SerializedName("pincode")
      private String pincode;

        public User(String name, String contact, String email, String address, String state, String city, String pincode) {
            this.name = name;
            this.contact = contact;
            this.email = email;
            this.address = address;
            this.state = state;
            this.city = city;
            this.pincode = pincode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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
}
