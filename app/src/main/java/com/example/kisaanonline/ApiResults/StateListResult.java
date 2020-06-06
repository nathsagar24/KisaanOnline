package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateListResult {

    @SerializedName("data")
    private List<StateDetails> data;

    public StateListResult(List<StateDetails> data) {
        this.data = data;
    }

    public List<StateDetails> getData() {
        return data;
    }

    public class StateDetails{

        @SerializedName("statename")
        private String stateName;

        public StateDetails(String stateName) {
            this.stateName = stateName;
        }

        public String getStateName() {
            return stateName;
        }

    }

}
