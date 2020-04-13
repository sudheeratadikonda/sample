package com.example.sample.modals;

public class DistrictData {
    private String state;
    private String districtname;
    private String districtcode;

    public DistrictData() {
    }

    public DistrictData(String state, String districtname, String districtcode) {
        this.state = state;
        this.districtname = districtname;
        this.districtcode = districtcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
