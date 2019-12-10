package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.dto.AudioRecordDto;
import io.cjf.jinterviewback.po.AudioRecord;

import java.util.List;

public interface AudioRecordMapper {
    int deleteByPrimaryKey(Integer audioRecordId);

    int insert(AudioRecord record);

    int insertSelective(AudioRecord record);

    AudioRecord selectByPrimaryKey(Integer audioRecordId);

    int updateByPrimaryKeySelective(AudioRecord record);

    int updateByPrimaryKeyWithBLOBs(AudioRecord record);

    int updateByPrimaryKey(AudioRecord record);

    AudioRecord selectAudioRecordByid(Integer interviewId);
    List<AudioRecordDto> search(String keyword, Long time);

    AudioRecordDto getByid(Integer audiorecordId);
}