package com.example.sample.modals;

public class StateData {
    private String state;
    private String statecode;

    public StateData() {
    }

    public StateData(String state, String statecode) {
        this.state = state;
        this.statecode = statecode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }
}
