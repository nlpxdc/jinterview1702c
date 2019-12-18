package io.cjf.jinterviewback.dto;

import java.util.Date;

public class InterviewListDTO {
    private Integer interviewId;
    private String company;
    private Integer studentId;
    private String studentName;
    private String nickName;
    private String avatarUrl;
    private Date time;
    private Byte status;

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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

//    @JsonIgnore
    public Date getTime() {
        return time;
    }

    public Long getTimestamp(){
        return this.time.getTime();
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
