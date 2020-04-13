package com.example.sample.modals;

public class MandalData {
    private String state;
    private String statecode;
    private String district;
    private String districtcode;
    private String mandal;
    private String mandalcode;

    public MandalData() {
    }

    public MandalData(String state, String statecode, String district, String districtcode, String mandal, String mandalcode) {
        this.state = state;
        this.statecode = statecode;
        this.district = district;
        this.districtcode = districtcode;
        this.mandal = mandal;
        this.mandalcode = mandalcode;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getMandal() {
        return mandal;
    }

    public void setMandal(String mandal) {
        this.mandal = mandal;
    }

    public String getMandalcode() {
        return mandalcode;
    }

    public void setMandalcode(String mandalcode) {
        this.mandalcode = mandalcode;
    }
}
