package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.dao.ExamPhotoMapper;
import io.cjf.jinterviewsite.po.ExamPhoto;
import io.cjf.jinterviewsite.service.ExamPhotoService;
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
