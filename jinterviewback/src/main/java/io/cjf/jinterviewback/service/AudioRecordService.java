package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.po.AudioRecord;

import java.util.List;

public interface AudioRecordService {
    AudioRecord getAudioRecordByid(Integer interviewId);


    List search(String keyword, Long time);


    AudioRecord getByid(Integer audiorecordId);
}
