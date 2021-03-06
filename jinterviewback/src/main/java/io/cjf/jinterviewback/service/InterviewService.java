package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.dto.StudentInterviewCountDTO;
import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.vo.InterviewNotificationVO;

import java.util.Date;
import java.util.List;

public interface InterviewService {

    Interview getById(Integer interviewId);

    void updateById(Interview interview);

    List<StudentInterviewCountDTO> getStudentInterviewCount();

    List<InterviewListDTO> search(String keyword, Integer studentId, Date time);

    Integer createInterview(String company, String address, Date time, Integer studentId);

    void deleteById(Integer interviewId);

    List<InterviewNotificationVO> getInterviewNotification(Date time);

    Long sendInterviewNotification(String openid, String company, String address, Date time);

}
