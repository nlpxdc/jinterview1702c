package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.po.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {
    int deleteByPrimaryKey(Integer studentId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer studentId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

//    custom

    Student selectByOpenid(@Param("openid") String openid);

    void updateStatus(@Param("studentId") Integer studentId);
}