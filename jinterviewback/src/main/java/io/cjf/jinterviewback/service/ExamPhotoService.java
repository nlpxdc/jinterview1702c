package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.po.ExamPhoto;

import java.util.List;

public interface ExamPhotoService {
    List<ExamPhoto> getExaminationPhotoById(Integer examId);
}
