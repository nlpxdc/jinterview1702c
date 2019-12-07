package io.cjf.jinterviewsite.service;

import io.cjf.jinterviewsite.po.Student;

public interface StudentService {
    Student getByOpenid(String openid);

    Integer createStudent(String openid, String nickname, String avatarUrl, Byte gender);

    void updateStudent(Student student);
}
