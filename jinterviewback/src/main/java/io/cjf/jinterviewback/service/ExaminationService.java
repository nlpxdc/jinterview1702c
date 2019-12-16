package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.dto.ExaminationExamByIdDTO;
import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.po.Examination;

import java.util.Date;
import java.util.List;

public interface ExaminationService {

    Examination getExamByInterviewId(Integer interviewId);

    List<ExaminationSearchDTO> search(String keyword, Date time);

    ExaminationExamByIdDTO getExamById(Integer examId);

    Integer createExamination(Examination examination);
}
