package io.cjf.jinterviewsite.dao;

import io.cjf.jinterviewsite.po.Examination;

public interface ExaminationMapper {
    int deleteByPrimaryKey(Integer examId);

    int insert(Examination record);

    int insertSelective(Examination record);

    Examination selectByPrimaryKey(Integer examId);

    int updateByPrimaryKeySelective(Examination record);

    int updateByPrimaryKey(Examination record);
}