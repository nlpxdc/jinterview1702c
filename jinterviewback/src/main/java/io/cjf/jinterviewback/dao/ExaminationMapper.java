package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.dto.ExaminationExamByIdDTO;
import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.po.Examination;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ExaminationMapper {
    int deleteByPrimaryKey(Integer examId);

    int insert(Examination record);

    int insertSelective(Examination record);

    Examination selectByPrimaryKey(Integer examId);

    int updateByPrimaryKeySelective(Examination record);

    int updateByPrimaryKey(Examination record);

    Examination selectExaminationById(Integer interviewId);

    List<ExaminationSearchDTO> search(@Param("keyword") String keyword, @Param("time") Date time);

    ExaminationExamByIdDTO getExamById(Integer examId);
}