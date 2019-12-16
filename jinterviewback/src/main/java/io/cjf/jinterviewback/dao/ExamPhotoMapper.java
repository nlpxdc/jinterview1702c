package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.po.ExamPhoto;
import org.apache.ibatis.annotations.Param;
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

    List<ExamPhoto> selectExamPhotoByExamId(@Param("examId") Integer examId);

    List<ExamPhoto> selectByFilename(@Param("filename") String filename);

    int deleteByExamId(@Param("examId") Integer examId);
}