package io.cjf.jinterviewsite.service;

import io.cjf.jinterviewsite.po.ExamPhoto;

import java.util.List;

public interface ExamPhotoService {
    List<ExamPhoto> getExaminationPhotoById(Integer examId);
}
