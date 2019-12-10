package io.cjf.jinterviewsite.service;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.po.AudioRecord;

import java.util.List;

public interface AudioRecordService {
    AudioRecord getAudioRecordByid(Integer interviewId);


    List search(String keyword, Long time);


    AudioRecord getByid(Integer audiorecordId);
}
