package com.upskillx.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String bio;
    private int age;
    private String gender;
    private String location;
    private String education;
    private String workLocation;
    private String linkedIn;
    private String skillLevel;
    private String uid;
    private String idToken;
    private String introVdoUrl;
    private String profileImg;
    private String portfolio;
    private String position;
    private List<String> skillsToTeach;
    private List<String> skillsToLearn;
    private List<String> prefFormat;
    private String prefToTeach;
    private List<String> certificateUrl = new ArrayList<>();
    private List<String> presenatationUrl = new ArrayList<>();
    private int creditPoints;
    private int testScore;
    private int starCount;
    private int totalCp;

    public int getTotalCp() {
        return totalCp;
    }

    public void setTotalCp(int totalCp) {
        this.totalCp = totalCp;
    }

    public void setTestScore(int testScore) {
        this.testScore = testScore;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public int getTestScore() {
        return testScore;
    }

    public int getStarCount() {
        return starCount;
    }

    public int getCp() {
        return creditPoints;
    }

    public void setCp(int creditPoints) {
        this.creditPoints = creditPoints;
    }

    public List<String> getPresenatationUrl() {
        return presenatationUrl;
    }

    public void setPresenatationUrl(List<String> presenatationUrl) {
        this.presenatationUrl = presenatationUrl;
    }

    public List<String> getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(List<String> certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public void addCertificateUrl(String url) {
        this.certificateUrl.add(url);
    }

    public String getPrefToTeach() {
        return prefToTeach;
    }

    public void setPrefToTeach(String prefToTeach) {
        this.prefToTeach = prefToTeach;
    }

    public User() {}

    public User(String email, String uid, String idToken) {
        this.email = email;
        this.uid = uid;
        this.idToken = idToken;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getIdToken() {
        return idToken;
    }
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

     public String getIntroVdoUrl() {
        return introVdoUrl;
    }

    public void setIntroVdoUrl(String introVdoUrl) {
        this.introVdoUrl = introVdoUrl;
    }
    public String getProfileImg() {
        return profileImg;
    }
    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
    
        
    public String getSkillLevel() {
        return skillLevel;
    }
    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }
      
    public String getLinkedIn() {
        return linkedIn;
    }
    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
    public String getPortfolio() {
        return portfolio;
    }
    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getWorkLocation() {
        return workLocation;
    }
    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public List<String> getSkillsToTeach() {
        return skillsToTeach;
    }
    public void setSkillsToTeach(List<String> skillsToTeach) {
        this.skillsToTeach = skillsToTeach;
    }
    public List<String> getSkillsToLearn() {
        return skillsToLearn;
    }
    public void setSkillsToLearn(List<String> skillsToLearn) {
        this.skillsToLearn = skillsToLearn;
    }
    public List<String> getPrefFormat() {
        return prefFormat;
    }
    public void setPrefFormat(List<String> prefFormat) {
        this.prefFormat = prefFormat;
    }

    
}
