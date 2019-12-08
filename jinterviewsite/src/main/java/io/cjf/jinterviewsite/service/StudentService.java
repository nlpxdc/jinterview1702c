package io.cjf.jinterviewsite.service;

import io.cjf.jinterviewsite.po.Student;

import java.util.List;

public interface StudentService {
    Student getByOpenid(String openid);

    Integer createStudent(Student student);

    void updateStudent(Student student);

    List<Student> getStudentInfo();
}
