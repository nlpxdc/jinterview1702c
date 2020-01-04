package io.cjf.jinterviewback.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.WechatService;
import io.cjf.jinterviewback.dao.InterviewMapper;
import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.dto.StudentInterviewCountDTO;
import io.cjf.jinterviewback.dto.WechatTemplateMessageDTO;
import io.cjf.jinterviewback.enumeration.InterviewStatus;
import io.cjf.jinterviewback.es.document.InterviewESDocument;
import io.cjf.jinterviewback.es.repository.InterviewESRepository;
import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.po.Student;
import io.cjf.jinterviewback.service.InterviewService;
import io.cjf.jinterviewback.service.StudentService;
import io.cjf.jinterviewback.vo.InterviewNotificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private InterviewESRepository interviewESRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private WechatService wechatService;

    @Value("${interview.notification.templateId}")
    private String templateId;

    @Value("${interview.notification.url}")
    private String interviewUrl;

    @Override
    @Transactional
    public void updateById(Interview interview) {
        interviewMapper.updateByPrimaryKey(interview);

        final Integer interviewId = interview.getInterviewId();
        final Optional<InterviewESDocument> interviewESDocumentOpt = interviewESRepository.findById(interviewId);
        final InterviewESDocument interviewESDocument = interviewESDocumentOpt.get();
        interviewESDocument.setNote(interview.getNote());
        interviewESRepository.save(interviewESDocument);
    }

    @Override
    public Interview getById(Integer interviewId) {
        return interviewMapper.selectByinterview(interviewId);
    }

    @Override
    public List<StudentInterviewCountDTO> getStudentInterviewCount() {
        final List<StudentInterviewCountDTO> studentInterviewCountDTOS = interviewMapper.selectStudentInterviewCount();
        return studentInterviewCountDTOS;
    }

    @Override
    public List<InterviewListDTO> search(String keyword, Integer studentId, Date time) {
        final List<InterviewListDTO> interviewListDTOS = interviewMapper.search(keyword, studentId, time);

//        final List<InterviewESDocument> interviewESDocuments = interviewESRepository.findByCompanyOrStudentNameOrNote(keyword);
//        final List<InterviewESDocument> interviewESDocuments = interviewESRepository.findByCompanyOrStudentNameOrNote(keyword);
        //todo search with multiple field

//        final LinkedList<InterviewListDTO> interviewListDTOS1 = new LinkedList<>();
//        for (InterviewESDocument interviewESDocument : interviewESDocuments) {
//            final Integer interviewId = interviewESDocument.getInterviewId();
//            final Interview interview = interviewMapper.selectByPrimaryKey(interviewId);
//        }

        return interviewListDTOS;
    }

    @Override
    @Transactional
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

        final InterviewESDocument interviewESDocument = new InterviewESDocument();
        interviewESDocument.setInterviewId(interviewId);
        interviewESDocument.setCompany(company);
        final Student student = studentService.getByStudentId(studentId);
        final String realname = student.getRealname();
        interviewESDocument.setStudentName(realname);

        final InterviewESDocument save = interviewESRepository.save(interviewESDocument);

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
