package com.example.sample.modals;

public class StateData {
    private String stateId;
    private String state;
    private String statecode;

    public StateData() {
    }

    public StateData(String stateId, String state, String statecode) {
        this.stateId = stateId;
        this.state = state;
        this.statecode = statecode;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
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
