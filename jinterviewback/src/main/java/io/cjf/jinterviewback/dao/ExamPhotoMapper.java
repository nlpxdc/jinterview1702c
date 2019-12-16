package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.po.ExamPhoto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamPhotoMapper {
    int deleteByPrimaryKey(Integer examPhotoId);

    int insert(ExamPhoto record);

    int insertSelective(ExamPhoto record);

    ExamPhoto selectByPrimaryKey(Integer examPhotoId);

    int updateByPrimaryKeySelective(ExamPhoto record);

    int updateByPrimaryKey(ExamPhoto record);

    List<ExamPhoto> selectExaminationPhotoById(Integer c);
}