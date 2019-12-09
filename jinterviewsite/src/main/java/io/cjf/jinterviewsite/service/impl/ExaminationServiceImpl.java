package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.dao.ExaminationMapper;
import io.cjf.jinterviewsite.dto.ExaminationSearchDTO;
import io.cjf.jinterviewsite.po.Examination;
import io.cjf.jinterviewsite.service.ExaminationService;
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
