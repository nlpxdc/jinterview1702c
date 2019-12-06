package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.dao.InterviewMapper;
import io.cjf.jinterviewsite.po.Interview;
import io.cjf.jinterviewsite.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private InterviewMapper interviewMapper;

    @Override
    public List<Interview> getStudentInterview() {
        List<Interview> studentInterview = interviewMapper.getStudentInterview();
        return studentInterview;
    }
}
