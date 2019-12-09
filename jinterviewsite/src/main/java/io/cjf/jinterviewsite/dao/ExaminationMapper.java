package io.cjf.jinterviewsite.dao;

import io.cjf.jinterviewsite.dto.ExaminationSearchDTO;
import io.cjf.jinterviewsite.po.Examination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExaminationMapper {
    int deleteByPrimaryKey(Integer examId);

    int insert(Examination record);

    int insertSelective(Examination record);

    Examination selectByPrimaryKey(Integer examId);

    int updateByPrimaryKeySelective(Examination record);

    int updateByPrimaryKey(Examination record);

    Examination selectExaminationById(Integer interviewId);

    List<ExaminationSearchDTO> search(@Param("keyword") String keyword, @Param("time")Long time);
}