package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.InterviewMapper;
import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.dto.TemplateMessageDTO;
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
    public void updateById(Interview record) {
        interviewMapper.updateByPrimaryKey(record);
    }

    @Override
    public Interview getById(Integer interviewId) {
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
        interview.setStars((byte) 0);

        interviewMapper.insert(interview);
        final Integer interviewId = interview.getInterviewId();
        return interviewId;
    }

    @Override
    public void deleteById(Integer interviewId) {
        interviewMapper.deleteByPrimaryKey(interviewId);
    }

    @Override
    public List<TemplateMessageDTO> getTemById(Integer interviewId) {
        return interviewMapper.getTemById(interviewId);
    }

    @Override
    public List<TemplateMessageDTO> getInterviewTime() {
        return interviewMapper.getInterviewTime();
    }
}
