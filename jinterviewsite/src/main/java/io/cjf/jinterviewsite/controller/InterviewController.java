package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.dto.InterviewCreateDTO;
import io.cjf.jinterviewsite.dto.InterviewUpdateDTO;
import io.cjf.jinterviewsite.po.AudioRecord;
import io.cjf.jinterviewsite.po.ExamPhoto;
import io.cjf.jinterviewsite.po.Examination;
import io.cjf.jinterviewsite.po.Interview;
import io.cjf.jinterviewsite.service.AudioRecordService;
import io.cjf.jinterviewsite.service.ExamPhotoService;
import io.cjf.jinterviewsite.service.ExaminationService;
import io.cjf.jinterviewsite.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    @GetMapping("/getById")
    public JSONObject getById(@RequestParam Integer interviewId){
        JSONObject interviewJson = new JSONObject();
        Interview interview=interviewService.getByinterviewid(interviewId);
        interviewJson.put("interviewId",interview.getInterviewId());
        interviewJson.put("company",interview.getCompany());
        interviewJson.put("address",interview.getAddress());
        interviewJson.put("interviewTime",interview.getInterviewTime());
        interviewJson.put("stars",interview.getStars());
        interviewJson.put("note",interview.getNote());
        interviewJson.put("offerUrl",interview.getOfferUrl());
        Examination examination=examinationService.getExamination(interview.getInterviewId());
        List<ExamPhoto> ExamPhotos = examPhotoService.getExaminationPhotoById(examination.getExamId());
        List<JSONObject> ExamPhotoJsons = ExamPhotos.stream().map(ExamPhoto -> {
            JSONObject ExamPhotoJson = new JSONObject();

            ExamPhotoJson.put("url", ExamPhoto.getUrl());
            return ExamPhotoJson;
        }).collect(Collectors.toList());
        interviewJson.put("examphotoUrls", ExamPhotoJsons);
        AudioRecord audioRecord=audioRecordService.getAudioRecordByid(interview.getInterviewId());
        interviewJson.put("audiorecordUrl",audioRecord.getUrl());
        return interviewJson;
    }

    @GetMapping("/search")
    public List<JSONObject> search(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false, defaultValue = "false") Boolean onlyme,
                                   @RequestParam(required = false) Long time){
        return null;
    }

    @PostMapping("/create")
    public Integer create(@RequestBody InterviewCreateDTO interviewCreateDTO){
        return null;
    }





}
