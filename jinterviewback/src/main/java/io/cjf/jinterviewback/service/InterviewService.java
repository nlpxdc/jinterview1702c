package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.dto.TemplateMessageDTO;
import io.cjf.jinterviewback.po.Interview;

import java.util.Date;
import java.util.List;

public interface InterviewService {

    Interview getById(Integer interviewId);

    void updateById(Interview interview);

    List getInterviewCount();

    List<InterviewListDTO> search(String keyword, Integer studentId, Date time);

    Integer createInterview(String company, String address, Date time, Integer studentId);

    void deleteById(Integer interviewId);

    List<TemplateMessageDTO> getTemById(Integer interviewId);

    List<TemplateMessageDTO> getInterviewTime();
}
