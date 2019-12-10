package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.po.Student;

public interface StudentService {
    Student getByOpenid(String openid);

    Integer createStudent(Student student);

    void updateStudent(Student student);
    void activateStudent(Integer studentId);

    Student getBystudentId(Integer studentId);
}
