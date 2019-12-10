package io.cjf.jinterviewback.dto;

import java.util.Date;

public class ExaminationSearchDTO  {

    private String   realname;
    private  Integer  examId;
    private  String company;
    private Date time;
    private Integer likes;

    public ExaminationSearchDTO(String realname, Integer examId, String company, Date time, Integer likes) {
        this.realname = realname;
        this.examId = examId;
        this.company = company;
        this.time = time;
        this.likes = likes;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public ExaminationSearchDTO() {

    }

    @Override
    public String toString() {
        return "ExaminationSearchDTO{" +
                "realname='" + realname + '\'' +
                ", examId=" + examId +
                ", company='" + company + '\'' +
                ", time=" + time +
                ", likes=" + likes +
                '}';
    }
}
