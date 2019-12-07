package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.dao.StudentMapper;
import io.cjf.jinterviewsite.po.Student;
import io.cjf.jinterviewsite.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
@EnableAutoConfiguration
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getByOpenid(String openid) {
        final Student student = studentMapper.selectByOpenid(openid);
        return student;
    }
}
