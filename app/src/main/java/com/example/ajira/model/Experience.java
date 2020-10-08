package com.example.ajira.model;

public class Experience {

    private String CompanyName;
    private String jobTitle;
    private String jobDuration;

    public Experience(String companyName, String jobTitle, String jobDuration) {
        CompanyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDuration = jobDuration;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDuration() {
        return jobDuration;
    }

    public void setJobDuration(String jobDuration) {
        this.jobDuration = jobDuration;
    }
}
