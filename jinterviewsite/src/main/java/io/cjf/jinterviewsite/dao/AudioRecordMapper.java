package io.cjf.jinterviewsite.dao;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.po.AudioRecord;

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
    List search(String keyword, Long time);

    AudioRecord getByid(Integer audiorecordId);
}