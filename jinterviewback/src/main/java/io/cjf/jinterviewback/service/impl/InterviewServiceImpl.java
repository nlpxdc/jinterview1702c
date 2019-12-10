package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.InterviewMapper;
import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewMapper interviewMapper;

    @Override
    public Interview selectByPrimaryKey(Integer interviewId) {
        return interviewMapper.selectByPrimaryKey(interviewId);
    }

    @Override
    public int updateByPrimaryKey(Interview record) {
        return interviewMapper.updateByPrimaryKey(record);
    }

    @Override
    public Interview getByinterviewid(Integer interviewId) {
        return interviewMapper.selectByinterview(interviewId);
    }

    @Override
    public List getInterviewCount() {

        List interviews = interviewMapper.getInterviewCount();

        return interviews;
    }
}
