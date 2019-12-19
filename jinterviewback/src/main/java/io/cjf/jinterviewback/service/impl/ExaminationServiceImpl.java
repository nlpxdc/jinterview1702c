package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.ExaminationMapper;
import io.cjf.jinterviewback.dto.ExamShowDTO;
import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.po.Examination;
import io.cjf.jinterviewback.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExaminationServiceImpl implements ExaminationService {

    @Autowired
    private ExaminationMapper examinationMapper;

    @Override
    public Examination getExamByInterviewId(Integer interviewId) {
        final Examination examination = examinationMapper.selectExamByInterviewId(interviewId);
        return examination;
    }

    @Override
    public List<ExaminationSearchDTO> search(String keyword, Date time) {
        final List<ExaminationSearchDTO> exams = examinationMapper.search(keyword, time);
        return exams;
    }

    @Override
    public ExamShowDTO getExamById(Integer examId) {
        final ExamShowDTO examShowDTO = examinationMapper.selectExamById(examId);
        return examShowDTO;
    }

    @Override
    public Integer createExamination(Examination examination) {
        examinationMapper.insert(examination);
        final Integer examId = examination.getExamId();
        return examId;
    }



    @Override
    public void updateByPrimaryKeySelective(Examination examination) {
        examinationMapper.updateByPrimaryKeySelective(examination);
    }

    @Override
    public Examination selectByPrimaryKey(Integer examId) {
        return examinationMapper.selectByPrimaryKey(examId);
    }

}
