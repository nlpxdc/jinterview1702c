package io.cjf.jinterviewback.dto;

import io.cjf.jinterviewback.po.ExamPhoto;

import java.util.Date;
import java.util.List;

public class ExaminationExamByIdDTO {

    private String   realname;
    private  Integer  examId;
    private  String company;
    private  String content;
    private Date time;
    private Integer likes;

    private List<ExamPhoto> ExamPhotolist;

    public ExaminationExamByIdDTO(String realname, Integer examId, String company, String content, Date time, Integer likes, List<ExamPhoto> examPhotolist) {
        this.realname = realname;
        this.examId = examId;
        this.company = company;
        this.content = content;
        this.time = time;
        this.likes = likes;
        ExamPhotolist = examPhotolist;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<ExamPhoto> getExamPhotolist() {
        return ExamPhotolist;
    }

    public void setExamPhotolist(List<ExamPhoto> examPhotolist) {
        ExamPhotolist = examPhotolist;
    }

    public ExaminationExamByIdDTO() {

    }

    @Override
    public String toString() {
        return "ExaminationExamByIdDTO{" +
                "realname='" + realname + '\'' +
                ", examId=" + examId +
                ", company='" + company + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", likes=" + likes +
                ", ExamPhotolist=" + ExamPhotolist +
                '}';
    }
}
