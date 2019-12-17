package io.cjf.jinterviewback.dto;

import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.po.Student;

public class InterviewShowDTO extends Interview {

    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
