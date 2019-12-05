package io.cjf.jinterviewsite.dao;

import io.cjf.jinterviewsite.po.AudioRecord;

public interface AudioRecordMapper {
    int deleteByPrimaryKey(Integer audioRecordId);

    int insert(AudioRecord record);

    int insertSelective(AudioRecord record);

    AudioRecord selectByPrimaryKey(Integer audioRecordId);

    int updateByPrimaryKeySelective(AudioRecord record);

    int updateByPrimaryKeyWithBLOBs(AudioRecord record);

    int updateByPrimaryKey(AudioRecord record);
}