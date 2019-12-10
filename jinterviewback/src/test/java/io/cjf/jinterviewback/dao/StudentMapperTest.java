package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.enumeration.StudentStatus;
import io.cjf.jinterviewback.po.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    private String openid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void selectByOpenid() {
        final Student student = studentMapper.selectByOpenid(openid);
        assertNotNull(student);
    }

    @Test
//    @Rollback(false)
    void insert(){
        final Student student = new Student();
        student.setOpenid(openid);
        student.setNickname("mynickname");
        student.setAvatarUrl("http://xxx.com/xxx.jpg");
        student.setGender((byte)0);
        student.setStatus((byte) StudentStatus.NotActivate.ordinal());
        student.setMobileVerified(false);
        student.setEmailVerified(false);

        studentMapper.insert(student);
        final Integer studentId = student.getStudentId();
        assertNotNull(studentId);
    }
}