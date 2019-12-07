package io.cjf.jinterviewsite.dao;

import io.cjf.jinterviewsite.po.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer studentId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer studentId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

//    custom

    Student selectByOpenid(@Param("openid") String openid);
}