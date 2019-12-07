package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.dao.InterviewMapper;
import io.cjf.jinterviewsite.po.Interview;
import io.cjf.jinterviewsite.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
