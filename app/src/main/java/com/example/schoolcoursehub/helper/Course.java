package com.example.schoolcoursehub.helper;

public class Course {
    private int courseId;
    private String courseName;
    private double courseCost;
    private String courseDuration;
    private int maxParticipants;
    private String startingDate;
    private String registrationClosingDate;
    private String publishDate;
    private int currentEnrollment;
    private int branchId;
    private String branchName;
    private String registrationDate;

    public Course() {
    }
    public Course(String courseName, double courseCost, String courseDuration, int maxParticipants,
                  String startingDate, String registrationClosingDate, String publishDate,
                  int currentEnrollment, int branchId) {
        this.courseName = courseName;
        this.courseCost = courseCost;
        this.courseDuration = courseDuration;
        this.maxParticipants = maxParticipants;
        this.startingDate = startingDate;
        this.registrationClosingDate = registrationClosingDate;
        this.publishDate = publishDate;
        this.currentEnrollment = currentEnrollment;
        this.branchId = branchId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(double courseCost) {
        this.courseCost = courseCost;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    public void setRegistrationClosingDate(String registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void setCurrentEnrollment(int currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public String getBranchName(){
        return branchName;
    }
    public void setBranchName(String branchName){
        this.branchName = branchName;
    }
    public String getRegistrationDate(){
        return registrationDate;
    }
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
