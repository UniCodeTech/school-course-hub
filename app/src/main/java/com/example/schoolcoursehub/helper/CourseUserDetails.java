package com.example.schoolcoursehub.helper;

public class CourseUserDetails {
    private int id;
    private int courseNo;
    private int userId;
    private String registrationDate;
    private double totalFee;
    private String courseName;
    private int branchId;
    private String registrationClosingDate;
    private String startingDate;
    private double courseCost;
    private String userName;
    private String branchName;


    public CourseUserDetails() {
    }

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Course Number
    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    // User ID
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Registration Date
    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Total Fee
    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    // Course Name
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Branch ID
    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    // Registration Closing Date
    public String getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    public void setRegistrationClosingDate(String registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }

    // Starting Date
    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    // Course Cost
    public double getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(double courseCost) {
        this.courseCost = courseCost;
    }

    // User Name
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Branch Name
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName){
        this.branchName = branchName;
    }
}
