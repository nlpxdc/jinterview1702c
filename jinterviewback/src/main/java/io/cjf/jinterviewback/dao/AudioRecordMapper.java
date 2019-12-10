package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.dto.AudioRecordDTO;
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
    //录音列表
    List<AudioRecordDTO> search(String keyword, Long time);

    //录音详情列表
    AudioRecord getByid(Integer audiorecordId);
}