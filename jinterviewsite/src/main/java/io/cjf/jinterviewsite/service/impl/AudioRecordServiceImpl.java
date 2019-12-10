package io.cjf.jinterviewsite.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.dao.AudioRecordMapper;
import io.cjf.jinterviewsite.po.AudioRecord;
import io.cjf.jinterviewsite.service.AudioRecordService;
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
    public List search(String keyword, Long time) {
        return audioRecordMapper.search(keyword,time);
    }

    @Override
    public AudioRecord getByid(Integer audiorecordId) {
        return audioRecordMapper.getByid(audiorecordId);
    }


}
