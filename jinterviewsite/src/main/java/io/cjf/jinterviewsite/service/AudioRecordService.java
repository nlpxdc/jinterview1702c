package io.cjf.jinterviewsite.service;

import io.cjf.jinterviewsite.po.AudioRecord;

public interface AudioRecordService {
    AudioRecord getAudioRecordByid(Integer interviewId);
}
