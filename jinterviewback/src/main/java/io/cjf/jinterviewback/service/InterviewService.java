package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.po.Interview;

import java.util.Date;
import java.util.List;

public interface InterviewService {

    Interview getByinterviewid(Integer interviewId);

    Interview selectByPrimaryKey(Integer interviewId);

    int updateByPrimaryKey(Interview record);

    List getInterviewCount();

    List<InterviewListDTO> search(String keyword, Integer studentId, Date time);

    Integer createInterview(String company, String address, Date time, Integer studentId);
}
