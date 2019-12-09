package io.cjf.jinterviewsite.service;

import io.cjf.jinterviewsite.dto.ExaminationSearchDTO;
import io.cjf.jinterviewsite.po.Examination;

import java.util.List;

public interface ExaminationService {


    Examination getExamination(Integer interviewId);

    List<ExaminationSearchDTO> search(String keyword, Long time);
}
