package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.po.Interview;
import io.cjf.jinterviewsite.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chart")
@CrossOrigin
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/getStudentInterview")
    public List<JSONObject> getStudentInterview(){
        List<Interview> studentInterview = chartService.getStudentInterview();
        List<JSONObject> studentInterviews = studentInterview.stream().map(studentInterview1 -> {
            JSONObject studentInterviewObject = new JSONObject();
            studentInterviewObject.put("company", studentInterview1.getCompany());
            studentInterviewObject.put("interviewTime", studentInterview1.getInterviewTime());
            studentInterviewObject.put("studentId", studentInterview1.getInterviewId());
            studentInterviewObject.put("offerUrl", studentInterview1.getOfferUrl());
            return studentInterviewObject;
        }).collect(Collectors.toList());
        return studentInterviews;
    }

}
