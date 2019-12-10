package io.cjf.jinterviewback.service.impl;

import io.cjf.jinterviewback.dao.AudioRecordMapper;
import io.cjf.jinterviewback.dto.AudioRecordDTO;
import io.cjf.jinterviewback.po.AudioRecord;
import io.cjf.jinterviewback.service.AudioRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudioRecordServiceImpl implements AudioRecordService {

    @Autowired
    private AudioRecordMapper audioRecordMapper;

    @Override
    public AudioRecord getAudioRecordByid(Integer interviewId) {
        return audioRecordMapper.selectAudioRecordByid(interviewId);
    }

    @Override
    public List<AudioRecordDTO> search(String keyword, Long time) {
        return audioRecordMapper.search(keyword,time);
    }

    @Override
    public AudioRecord getByid(Integer audiorecordId) {
        return audioRecordMapper.getByid(audiorecordId);
    }


}
