package io.cjf.jinterviewback.dto;

import java.util.Date;

public class TemplateMessageDTO {

    private Integer interviewId;
    private String openId;
    private String company;
    private String address;
    private Date interview_time;

    public Integer getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getInterview_time() {
        return interview_time;
    }

    public void setInterview_time(Date interview_time) {
        this.interview_time = interview_time;
    }
}
