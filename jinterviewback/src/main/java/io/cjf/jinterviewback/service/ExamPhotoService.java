package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.po.ExamPhoto;

import java.util.List;

public interface ExamPhotoService {
    List<ExamPhoto> getExamPhotoByExamId(Integer examId);

    List<ExamPhoto> getByFilename(String filename);

    Integer createExamPhoto(ExamPhoto examPhoto);

    Integer deleteByExamId(Integer examId);
}
