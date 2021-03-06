package io.cjf.jinterviewback.service;

import io.cjf.jinterviewback.dto.AudioRecordDTO;
import io.cjf.jinterviewback.po.AudioRecord;

import java.util.List;

public interface AudioRecordService {
    AudioRecord getAudioRecordByInterviewId(Integer interviewId);


    List<AudioRecordDTO> search(String keyword, Long time);


    AudioRecordDTO getByid(Integer audiorecordId);


    void insertAudio(AudioRecord audioRecord);

    List<AudioRecord> getAudioByInterviewId(Integer interviewId);
}
