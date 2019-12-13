package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.dto.InterviewCreateDTO;
import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.dto.InterviewUpdateDTO;
import io.cjf.jinterviewback.enumeration.InterviewStatus;
import io.cjf.jinterviewback.exception.ClientException;
import io.cjf.jinterviewback.po.AudioRecord;
import io.cjf.jinterviewback.po.ExamPhoto;
import io.cjf.jinterviewback.po.Examination;
import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.service.AudioRecordService;
import io.cjf.jinterviewback.service.ExamPhotoService;
import io.cjf.jinterviewback.service.ExaminationService;
import io.cjf.jinterviewback.service.InterviewService;
import io.cjf.jinterviewback.util.ExeclUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;


    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private ExamPhotoService examPhotoService;

    @Autowired
    private AudioRecordService audioRecordService;

    @Autowired
    private ExeclUtil execlUtil;
    @GetMapping("/getById")
    public JSONObject getById(@RequestParam Integer interviewId){
        JSONObject interviewJson = new JSONObject();
        Interview interview=interviewService.getByinterviewid(interviewId);
        interviewJson.put("interviewId",interview.getInterviewId());
        interviewJson.put("company",interview.getCompany());
        interviewJson.put("address",interview.getAddress());

        interviewJson.put("time",interview.getInterviewTime().getTime());
        interviewJson.put("stars",interview.getStars());
        interviewJson.put("status",interview.getStatus());
        interviewJson.put("note",interview.getNote());
        interviewJson.put("offerUrl",interview.getOfferUrl());
        Examination examination=examinationService.getExamination(interview.getInterviewId());
        List<JSONObject> ExamPhotoJsons = new ArrayList<>();
        if (examination != null){
            List<ExamPhoto> ExamPhotos = examPhotoService.getExaminationPhotoById(examination.getExamId());
            ExamPhotoJsons = ExamPhotos.stream().map(ExamPhoto -> {
                JSONObject ExamPhotoJson = new JSONObject();
                ExamPhotoJson.put("url", ExamPhoto.getUrl());
                return ExamPhotoJson;
            }).collect(Collectors.toList());
        }
        interviewJson.put("examphotoUrls", ExamPhotoJsons);
        AudioRecord audioRecord=audioRecordService.getAudioRecordByid(interview.getInterviewId());
        String audioRecordUrl = audioRecord != null ? audioRecord.getUrl() : "";
        interviewJson.put("audiorecordUrl",audioRecordUrl);
        return interviewJson;
    }

    @GetMapping("/search")
    public List<InterviewListDTO> search(@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false, defaultValue = "false") Boolean onlyme,
                                         @RequestParam(required = false) Long time,
                                         @RequestAttribute Integer studentId){

        Integer studentIdDB = (onlyme ? studentId : null);
        Date date = (time == null ? null : new Date(time));

        List<InterviewListDTO> interviews = interviewService.search(keyword, studentIdDB, date);

        return interviews;
    }
    @GetMapping(value = "/downloadinterview",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] downloadinterview() throws IOException {

        List<InterviewListDTO> interviewListDTOS = interviewService.search(null, null, null);
        String filename = execlUtil.appendExeclBodyInfo(interviewListDTOS);
        FileInputStream fis = new FileInputStream(filename);
        byte[] bytes = StreamUtils.copyToByteArray(fis);

        return bytes;
    }

    @PostMapping("/create")
    public Integer create(@RequestBody InterviewCreateDTO interviewCreateDTO, @RequestAttribute Integer studentId){
        final String company = interviewCreateDTO.getCompany();
        final String address = interviewCreateDTO.getAddress();
        final Long time = interviewCreateDTO.getTime();
        final Date date = new Date(time);
        final Integer interviewId = interviewService.createInterview(company, address, date, studentId);
        return interviewId;
    }

    @PostMapping("/update")
    public void update(@RequestBody InterviewUpdateDTO interviewUpdateDTO) throws ClientException {
        final Integer interviewId = interviewUpdateDTO.getInterviewId();
        Interview interview = interviewService.selectByPrimaryKey(interviewId);
        if (interview == null){
            throw new ClientException(ClientExceptionConstant.INTERVIEW_NOT_EXIST_ERRCODE, ClientExceptionConstant.INTERVIEW_NOT_EXIST_ERRMSG);
        }
        interview.setNote(interviewUpdateDTO.getNote());
        interview.setStars(interviewUpdateDTO.getStars());
        interview.setStatus(interviewUpdateDTO.getStatus());
        interview.setInterviewTime(new Date(interviewUpdateDTO.getTime()));

        interviewService.updateByPrimaryKey(interview);
    }





}
