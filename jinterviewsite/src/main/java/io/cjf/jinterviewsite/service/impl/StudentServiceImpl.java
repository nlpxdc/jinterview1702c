package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.dao.StudentMapper;
import io.cjf.jinterviewsite.enumeration.StudentStatus;
import io.cjf.jinterviewsite.po.Student;
import io.cjf.jinterviewsite.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getByOpenid(String openid) {
        final Student student = studentMapper.selectByOpenid(openid);
        return student;
    }

    @Override
    public Integer createStudent(String openid, String nickname, String avatarUrl, Byte gender) {
        final Student student = new Student();
        student.setOpenid(openid);
        student.setNickname(nickname);
        student.setAvatarUrl(avatarUrl);
        student.setGender(gender);
        student.setStatus((byte)StudentStatus.Disable.ordinal());
        student.setMobileVerified(false);
        student.setEmailVerified(false);

        studentMapper.insert(student);
        final Integer studentId = student.getStudentId();
        return studentId;
    }
}
