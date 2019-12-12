package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.service.InterviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InterviewServiceImplTest {

    @Autowired
    private InterviewService interviewService;

    @Test
    void createInterview() {
        final Integer interviewId = interviewService.createInterview("永久", "南市区苏州河", new Date(), 1);
        assertNotNull(interviewId);
        assertTrue(interviewId != 0);
    }
}