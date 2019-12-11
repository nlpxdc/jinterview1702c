package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.dto.InterviewListDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InterviewMapperTest {

    @Autowired
    private InterviewMapper interviewMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void search() {
        Integer studentId = null;
        Date time = null;
        String keyword = "";
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);
        assertNotNull(interviewListDTOS);
        assertTrue(interviewListDTOS.size() != 0);
    }
}