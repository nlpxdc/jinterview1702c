package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.dao.StudentMapper;
import io.cjf.jinterviewsite.enumeration.StudentStatus;
import io.cjf.jinterviewsite.po.Student;
import io.cjf.jinterviewsite.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Integer createStudent(Student student) {

        student.setStatus((byte)StudentStatus.NotActivate.ordinal());
        student.setMobileVerified(false);
        student.setEmailVerified(false);

        studentMapper.insert(student);
        final Integer studentId = student.getStudentId();
        return studentId;
    }

    @Override
    public void updateStudent(Student student) {
        studentMapper.updateByPrimaryKey(student);
    }

}
