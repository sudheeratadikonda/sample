package com.example.sample.modals;

public class DistrictData {
    private String state;
    private String statecode;
    private String distId;
    private String districtname;
    private String districtcode;

    public DistrictData() {
    }

    public DistrictData(String state, String statecode, String distId, String districtname, String districtcode) {
        this.state = state;
        this.statecode = statecode;
        this.distId = distId;
        this.districtname = districtname;
        this.districtcode = districtcode;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }
}
