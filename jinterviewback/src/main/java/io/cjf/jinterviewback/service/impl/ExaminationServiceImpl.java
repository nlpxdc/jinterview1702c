package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.ExaminationMapper;
import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.po.Examination;
import io.cjf.jinterviewback.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationServiceImpl implements ExaminationService {
    @Autowired
    private ExaminationMapper examinationMapper;


    @Override
    public Examination getExamination(Integer interviewId) {
        return examinationMapper.selectExaminationById(interviewId);
    }

    @Override
    public List<ExaminationSearchDTO> search(String keyword,Long time) {
        return examinationMapper.search(keyword,time);
    }
}