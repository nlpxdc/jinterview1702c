package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.ExamPhotoMapper;
import io.cjf.jinterviewback.dao.ExaminationMapper;
import io.cjf.jinterviewback.dto.ExaminationExamByIdDTO;
import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.po.ExamPhoto;
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
    @Autowired
    private ExamPhotoMapper examPhotoMapper;

    @Override
    public Examination getExamination(Integer interviewId) {
        return examinationMapper.selectExaminationById(interviewId);
    }

    @Override
    public List<ExaminationSearchDTO> search(String keyword, Date time) {
        return examinationMapper.search(keyword,time);
    }

    @Override
    public ExaminationExamByIdDTO getExamById(Integer examId) {
        ExaminationExamByIdDTO examinationExamByIdDTO = examinationMapper.getExamById(examId);
        Integer examId1 = examinationExamByIdDTO.getExamId();
        List<ExamPhoto>  list = examPhotoMapper.selectExaminationPhotoById(examId1);
        examinationExamByIdDTO.setExamPhotolist(list);
        return examinationExamByIdDTO;
    }
}
