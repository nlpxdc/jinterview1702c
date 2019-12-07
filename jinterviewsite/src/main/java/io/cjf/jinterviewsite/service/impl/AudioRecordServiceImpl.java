package io.cjf.jinterviewsite.service.impl;

import io.cjf.jinterviewsite.dao.AudioRecordMapper;
import io.cjf.jinterviewsite.po.AudioRecord;
import io.cjf.jinterviewsite.service.AudioRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudioRecordServiceImpl implements AudioRecordService {
    @Autowired
    private AudioRecordMapper audioRecordMapper;
    @Override
    public AudioRecord getAudioRecordByid(Integer interviewId) {
        return audioRecordMapper.selectAudioRecordByid(interviewId);
    }
}
