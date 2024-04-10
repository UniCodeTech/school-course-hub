package com.example.schoolcoursehub.helper;

public class Branch {
    private int branchId;
    private String branchName;
    private double latitude;
    private double longitude;


    public Branch(){

    };
    public Branch(int branchId, String branchName, double latitude, double longitude) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
