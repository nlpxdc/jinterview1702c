package io.cjf.jinterviewback.dto;

import java.util.Date;

public class AudioRecordDto {
    private Integer   audiorecordId;
    private  Integer  interviewId;
    private  String company;
    private String student;
    private Integer likes;

    public Integer getAudiorecordId() {
        return audiorecordId;
    }

    public void setAudiorecordId(Integer audiorecordId) {
        this.audiorecordId = audiorecordId;
    }

    public Integer getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
