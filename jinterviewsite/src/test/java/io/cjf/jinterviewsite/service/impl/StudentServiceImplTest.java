package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.po.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceImplTest {

    private String openid = UUID.randomUUID().toString();

    @Autowired
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getByOpenid() {
        final Student student = studentService.getByOpenid(openid);
        assertNotNull(student);
    }

    @Test
    void createStudent() {
        String openid = UUID.randomUUID().toString();
        String nickname = "mynickname";
        String avatarUrl = "http://xxx.com/xxx.jpg";
        Byte gender = 0;
        final Integer studentId = studentService.createStudent(openid, nickname, avatarUrl, gender);
        assertNotNull(studentId);

    }
}