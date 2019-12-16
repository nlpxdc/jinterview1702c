package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.ExamPhotoMapper;
import io.cjf.jinterviewback.po.ExamPhoto;
import io.cjf.jinterviewback.service.ExamPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamPhotoServiceImpl implements ExamPhotoService {

    @Autowired
    private ExamPhotoMapper examPhotoMapper;

    @Override
    public List<ExamPhoto> getExaminationPhotoById(Integer examId) {
        final List<ExamPhoto> examPhotos = examPhotoMapper.selectExaminationPhotoById(examId);
        return examPhotos;
    }

    @Override
    public Integer createExamPhoto(ExamPhoto examPhoto) {
        examPhotoMapper.insert(examPhoto);
        final Integer examId = examPhoto.getExamId();
        return examId;
    }
}
