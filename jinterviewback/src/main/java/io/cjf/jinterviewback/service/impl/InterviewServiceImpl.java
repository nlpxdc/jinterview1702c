package io.cjf.jinterviewback.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.WechatApi;
import io.cjf.jinterviewback.client.WechatService;
import io.cjf.jinterviewback.dao.InterviewMapper;
import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.dto.TemplateMessageDTO;
import io.cjf.jinterviewback.dto.WechatTemplateMessageDTO;
import io.cjf.jinterviewback.enumeration.InterviewStatus;
import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.service.InterviewService;
import io.cjf.jinterviewback.vo.InterviewNotificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private WechatService wechatService;

    @Value("${interview.notification.templateId}")
    private String templateId;

    @Value("${interview.notification.url}")
    private String interviewUrl;

    @Override
    public void updateById(Interview record) {
        interviewMapper.updateByPrimaryKey(record);
    }

    @Override
    public Interview getById(Integer interviewId) {
        return interviewMapper.selectByinterview(interviewId);
    }

    @Override
    public List getInterviewCount() {
        List interviews = interviewMapper.getInterviewCount();
        return interviews;
    }

    @Override
    public List<InterviewListDTO> search(String keyword, Integer studentId, Date time) {
        return interviewMapper.search(keyword, studentId, time);
    }

    @Override
    public Integer createInterview(String company, String address, Date time, Integer studentId) {
        final Interview interview = new Interview();
        interview.setCompany(company);
        interview.setAddress(address);
        interview.setInterviewTime(time);
        interview.setStudentId(studentId);
        interview.setCreateTime(new Date());
        interview.setStatus((byte)InterviewStatus.待面试.ordinal());
        interview.setStars((byte) 0);

        interviewMapper.insert(interview);
        final Integer interviewId = interview.getInterviewId();
        return interviewId;
    }

    @Override
    public void deleteById(Integer interviewId) {
        interviewMapper.deleteByPrimaryKey(interviewId);
    }

    @Override
    public List<InterviewNotificationVO> getInterviewNotification(Date time) {
        final long timestamp = time.getTime();
        long startTimestamp = timestamp + 2 * 60 * 60 * 1000;
        long endTimestamp = timestamp + 3 * 60 * 60 * 1000;
        Date startTime = new Date(startTimestamp);
        Date endTime = new Date(endTimestamp);
        final List<InterviewNotificationVO> interviewNotificationVOS = interviewMapper.selectBetweenTime(startTime, endTime);
        return interviewNotificationVOS;
    }

    @Override
    public Long sendInterviewNotification(String openid, String company, String address, Date time) {

        final WechatTemplateMessageDTO templateMessageDTO = new WechatTemplateMessageDTO();
        templateMessageDTO.setTouser(openid);
        templateMessageDTO.setTemplateId(templateId);
        templateMessageDTO.setUrl(interviewUrl);

        final JSONObject dataJson = new JSONObject();

        final JSONObject companyObj = new JSONObject();
        companyObj.put("value", company);
        companyObj.put("color", "#ff0000");
        dataJson.put("company", companyObj);

        final JSONObject timeObj = new JSONObject();
        timeObj.put("value", time.toString());
        timeObj.put("color", "#ff0000");
        dataJson.put("time", timeObj);

        final JSONObject addressObj = new JSONObject();
        addressObj.put("value", address);
        addressObj.put("color", "#ff0000");
        dataJson.put("address", addressObj);

        templateMessageDTO.setData(dataJson);

        final Long msgId = wechatService.sendTemplateMessage(templateMessageDTO);

        return msgId;
    }

}
