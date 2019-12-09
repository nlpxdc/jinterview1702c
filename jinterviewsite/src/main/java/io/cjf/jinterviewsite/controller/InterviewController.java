package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.dto.InterviewCreateDTO;
import io.cjf.jinterviewsite.dto.InterviewListDTO;
import io.cjf.jinterviewsite.dto.InterviewUpdateDTO;
import io.cjf.jinterviewsite.enumType.Status;
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
@CrossOrigin
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
    public JSONObject getById(@RequestParam Integer interviewId) throws ParseException {
        JSONObject interviewJson = new JSONObject();
        Interview interview=interviewService.getByinterviewid(interviewId);
        interviewJson.put("interviewId",interview.getInterviewId());
        interviewJson.put("company",interview.getCompany());
        interviewJson.put("address",interview.getAddress());
        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = format1.format(interview.getInterviewTime());

        interviewJson.put("interviewTime",format);
        interviewJson.put("stars",interview.getStars());
        Status status = Status.values()[interview.getStatus()];

        interviewJson.put("status",status);
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
    public List<InterviewListDTO> search(@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false, defaultValue = "false") Boolean onlyme,
                                         @RequestParam(required = false) Long time){



        return null;
    }

    @PostMapping("/create")
    public Integer create(@RequestBody InterviewCreateDTO interviewCreateDTO){
        return null;
    }

    @PostMapping("/update")
    public void update(@RequestBody InterviewUpdateDTO interviewUpdateDTO) throws ParseException {
        Interview interview = interviewService.selectByPrimaryKey(interviewUpdateDTO.getInterviewId());
        interview.setNote(interviewUpdateDTO.getNote());
        interview.setStars(interviewUpdateDTO.getStars());
        interview.setStatus(interviewUpdateDTO.getStatus());
        interview.setCompany(interviewUpdateDTO.getCompany());
        interview.setAddress(interviewUpdateDTO.getAddress());
        long msl=(long)interviewUpdateDTO.getTime()*1000;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp=null;
        if(interviewUpdateDTO.getTime()!=null){
            try {
                String str=sdf.format(msl);
                temp=sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        interview.setInterviewTime(temp);
        interview.setInterviewId(interviewUpdateDTO.getInterviewId());

        interviewService.updateByPrimaryKey(interview);
    }





}
