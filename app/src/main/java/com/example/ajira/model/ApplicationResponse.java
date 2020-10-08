package com.example.ajira.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ApplicationResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("Logo")
    @Expose
    private String logo;
    @SerializedName("Job")
    @Expose
    private String job;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("status")
    @Expose
    private String status;

    public ApplicationResponse(Integer id, String company, String logo, String job, String salary, String status) {
        this.id = id;
        this.company = company;
        this.logo = logo;
        this.job = job;
        this.salary = salary;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
