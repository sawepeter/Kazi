package com.example.ajira.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkerRequest {

    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("skill_name")
    @Expose
    private String skillName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
