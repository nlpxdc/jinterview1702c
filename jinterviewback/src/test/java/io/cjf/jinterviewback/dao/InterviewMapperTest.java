package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.dto.InterviewListDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Test
    void search2() {
        Integer studentId = 7;
        Date time = new Date();
        String keyword = "";
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);
        assertNotNull(interviewListDTOS);
        assertTrue(interviewListDTOS.size() != 0);
    }

    @Test
    void search3() {
        Integer studentId = null;
        Date time = null;
        String keyword = "科技";
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);
        assertNotNull(interviewListDTOS);
        assertTrue(interviewListDTOS.size() != 0);
    }

    @Test
    void search4() {
        Integer studentId = 6;
        Date time = null;
        String keyword = "科技";
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);
        assertNotNull(interviewListDTOS);
        assertTrue(interviewListDTOS.size() != 0);
    }

    @Test
    void search5() throws ParseException {
        Integer studentId = null;
        String time1="2019-12-01 19:02:08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(time1);
        String keyword = "";
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);
        assertNotNull(interviewListDTOS);
        assertTrue(interviewListDTOS.size() != 0);
    }

    @Test
    void search6() throws ParseException {
        Integer studentId = 4;
        String time1="2019-12-01 19:02:08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(time1);
        String keyword = "";
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);
        assertNotNull(interviewListDTOS);
        assertTrue(interviewListDTOS.size() != 0);
    }

    @Test
    void search7() throws ParseException {
        Integer studentId = null;
        String time1="2019-12-01 19:02:08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(time1);
        String keyword = "小年糕";
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);
        assertNotNull(interviewListDTOS);
        assertTrue(interviewListDTOS.size() != 0);
    }

    @Test
    void search8() throws ParseException {
        Integer studentId = 4;
        String time1="2019-12-12 19:02:08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(time1);
        String keyword = "小年糕";
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);
        assertNotNull(interviewListDTOS);
        assertTrue(interviewListDTOS.size() != 0);
    }
}