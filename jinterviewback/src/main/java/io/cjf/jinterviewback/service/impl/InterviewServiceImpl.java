package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.InterviewMapper;
import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.enumeration.InterviewStatus;
import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public List<InterviewListDTO> search(String keyword, Integer studentId, Date time) {
        return interviewMapper.search(keyword, studentId, time);
    }

    @Override
    public Integer createInterview(String company, String address, Date time, Integer studentId) {
        final Interview interview = new Interview();
        interview.setCompany(company);
        interview.setAddress(address);
        interview.setInterviewTime(time);
        interview.setStudentId(studentId);
        interview.setCreateTime(new Date());
        interview.setStatus((byte)InterviewStatus.待面试.ordinal());

        interviewMapper.insert(interview);
        final Integer interviewId = interview.getInterviewId();
        return interviewId;
    }
}
