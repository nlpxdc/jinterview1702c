package io.cjf.jinterviewsite.dao;

import io.cjf.jinterviewsite.po.Interview;

import java.util.List;

public interface InterviewMapper {
    int deleteByPrimaryKey(Integer interviewId);

    int insert(Interview record);

    int insertSelective(Interview record);

    Interview selectByPrimaryKey(Integer interviewId);

    int updateByPrimaryKeySelective(Interview record);

    int updateByPrimaryKey(Interview record);
    //
    List<Interview> getStudentInterview();

    Interview selectByinterview(Integer interviewId);
}
