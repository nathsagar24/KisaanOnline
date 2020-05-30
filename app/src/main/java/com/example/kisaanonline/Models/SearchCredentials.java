package com.example.kisaanonline.Models;

public class SearchCredentials {
    private String search_keyword;
    private int min_price,max_price,start ;

    public SearchCredentials(String search_keyword, int min_price, int max_price, int start) {
        this.search_keyword = search_keyword;
        this.min_price = min_price;
        this.max_price = max_price;
        this.start = start;
    }

    public String getSearch_keyword() {
        return search_keyword;
    }

    public void setSearch_keyword(String search_keyword) {
        this.search_keyword = search_keyword;
    }

    public int getMin_price() {
        return min_price;
    }

    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

    public int getMax_price() {
        return max_price;
    }

    public void setMax_price(int max_price) {
        this.max_price = max_price;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
