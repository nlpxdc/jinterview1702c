package io.cjf.jinterviewsite.service;

import io.cjf.jinterviewsite.po.Interview;

public interface InterviewService {

    Interview getByinterviewid(Integer interviewId);

    Interview selectByPrimaryKey(Integer interviewId);

    int updateByPrimaryKey(Interview record);
}