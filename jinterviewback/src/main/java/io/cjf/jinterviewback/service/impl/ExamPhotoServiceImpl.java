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
        return examPhotoMapper.selectExaminationPhotoById(examId);
    }
}
