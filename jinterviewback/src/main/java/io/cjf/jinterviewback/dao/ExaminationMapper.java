package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.dto.ExamShowDTO;
import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.po.Examination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExaminationMapper {
    int deleteByPrimaryKey(Integer examId);

    int insert(Examination record);

    int insertSelective(Examination record);

    Examination selectByPrimaryKey(Integer examId);

    int updateByPrimaryKeySelective(Examination record);

    int updateByPrimaryKey(Examination record);

    Examination selectExamByInterviewId(@Param("interviewId") Integer interviewId);

    List<ExaminationSearchDTO> search(@Param("keyword") String keyword, @Param("time") Date time);

    ExamShowDTO selectExamById(Integer examId);
}