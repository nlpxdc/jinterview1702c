package io.cjf.jinterviewsite.service;

import io.cjf.jinterviewsite.po.Student;

public interface StudentService {
    Student getByOpenid(String openid);

    Integer createStudent(Student student);

    void updateStudent(Student student);
}
