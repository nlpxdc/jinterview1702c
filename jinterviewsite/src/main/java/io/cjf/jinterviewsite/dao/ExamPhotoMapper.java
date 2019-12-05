package io.cjf.jinterviewsite.dao;

import io.cjf.jinterviewsite.po.ExamPhoto;

public interface ExamPhotoMapper {
    int deleteByPrimaryKey(Integer examPhotoId);

    int insert(ExamPhoto record);

    int insertSelective(ExamPhoto record);

    ExamPhoto selectByPrimaryKey(Integer examPhotoId);

    int updateByPrimaryKeySelective(ExamPhoto record);

    int updateByPrimaryKey(ExamPhoto record);
}