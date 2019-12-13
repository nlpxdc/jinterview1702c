package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.StudentMapper;
import io.cjf.jinterviewback.enumeration.StudentStatus;
import io.cjf.jinterviewback.po.Student;
import io.cjf.jinterviewback.service.StudentService;
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


    @Override
    public void activateStudent(Integer studentId) {
        final Student student = studentMapper.selectByPrimaryKey(studentId);
        student.setStatus((byte)StudentStatus.Activate.ordinal());
        student.setMobileVerified(true);
        studentMapper.updateByPrimaryKey(student);
    }
    @Override
    public void activateStudentMail(Integer studentId) {
        final Student student = studentMapper.selectByPrimaryKey(studentId);
        student.setStatus((byte)StudentStatus.Activate.ordinal());
        student.setEmailVerified(true);
        studentMapper.updateByPrimaryKey(student);
    }

    @Override
    public Student getBystudentId(Integer studentId) {
        return studentMapper.selectByPrimaryKey(studentId);
    }

    @Override
    public Student selectByPrimaryKey(Integer studentId) {
        return studentMapper.selectByPrimaryKey(studentId);
    }
}
