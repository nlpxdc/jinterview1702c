package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.dto.AudioRecordDto;
import io.cjf.jinterviewback.po.AudioRecord;

import java.util.List;

public interface AudioRecordService {
    AudioRecord getAudioRecordByid(Integer interviewId);


    List<AudioRecordDto> search(String keyword, Long time);


    AudioRecordDto getByid(Integer audiorecordId);
}
