package io.cjf.jinterviewback.dto;

import java.io.Serializable;

public class StudentInterviewCountDTO implements Serializable {
    private String studentName;
    private Integer interviewCount;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getInterviewCount() {
        return interviewCount;
    }

    public void setInterviewCount(Integer interviewCount) {
        this.interviewCount = interviewCount;
    }
}
