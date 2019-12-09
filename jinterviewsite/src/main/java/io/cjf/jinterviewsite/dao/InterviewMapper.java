package io.cjf.jinterviewsite.dao;

import io.cjf.jinterviewsite.dto.InterviewListDTO;
import io.cjf.jinterviewsite.po.Interview;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InterviewMapper {
    int deleteByPrimaryKey(Integer interviewId);

    int insert(Interview record);

    int insertSelective(Interview record);

    Interview selectByPrimaryKey(Integer interviewId);

    int updateByPrimaryKeySelective(Interview record);

    int updateByPrimaryKey(Interview record);
    //
    List<Interview> getStudentInterview();

    Interview selectByinterview(Integer interviewId);

    List getInterviewCount();

    List<InterviewListDTO> search(@Param("keyword") String keyword,
                                  @Param("studentId") Integer studentId,
                                  @Param("time") Date time);
}
