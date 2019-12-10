package io.cjf.jinterviewback.controller;

import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chart")
@CrossOrigin
public class ChartController {

    @Autowired
    private InterviewService interviewService;


    @GetMapping("/interviewCount")
    public Map<String, Long> interviewCount(){


        List<Interview> interviews = interviewService.getInterviewCount();

        Map<String, Long> interCount = interviews.stream().collect(
                Collectors.groupingBy(Interview::getRealName, Collectors.counting()));
        return interCount;
    }


}
