package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.po.Examination;

import java.util.List;

public interface ExaminationService {


    Examination getExamination(Integer interviewId);

    List<ExaminationSearchDTO> search(String keyword, Long time);
}
