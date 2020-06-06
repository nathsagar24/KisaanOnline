package com.example.kisaanonline.Models;

public class SearchCredentials {
    private String search_keyword;
    private Integer limit, start;

    public SearchCredentials(String search_keyword, Integer start, Integer limit) {
        this.search_keyword = search_keyword;
        this.start = start;
        this.limit = limit;
    }

    public String getSearch_keyword() {
        return search_keyword;
    }

    public void setSearch_keyword(String search_keyword) {
        this.search_keyword = search_keyword;
    }

    public int getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
