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
    public List<ExamPhoto> getExamPhotoByExamId(Integer examId) {
        final List<ExamPhoto> examPhotos = examPhotoMapper.selectExamPhotoByExamId(examId);
        return examPhotos;
    }

    @Override
    public List<ExamPhoto> getByFilename(String filename) {
        final List<ExamPhoto> examPhotos = examPhotoMapper.selectByFilename(filename);
        return examPhotos;
    }

    @Override
    public Integer createExamPhoto(ExamPhoto examPhoto) {
        examPhotoMapper.insert(examPhoto);
        final Integer examId = examPhoto.getExamId();
        return examId;
    }

    @Override
    public Integer deleteByExamId(Integer examId) {
        final int count = examPhotoMapper.deleteByExamId(examId);
        return count;
    }
}
