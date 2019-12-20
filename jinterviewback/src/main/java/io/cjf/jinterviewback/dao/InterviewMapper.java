package io.cjf.jinterviewback.dao;

import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.dto.TemplateMessageDTO;
import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.vo.InterviewNotificationVO;
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

    Interview selectByinterview(Integer interviewId);

    List getInterviewCount();

    List<InterviewListDTO> search(@Param("keyword") String keyword,
                                  @Param("studentId") Integer studentId,
                                  @Param("time") Date time);

    List<InterviewNotificationVO> selectBetweenTime(@Param("startTime") Date startTime,
                                                    @Param("endTime") Date endTime);

}
