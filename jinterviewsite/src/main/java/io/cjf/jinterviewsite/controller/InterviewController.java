package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.dto.InterviewCreateDTO;
import io.cjf.jinterviewsite.dto.InterviewUpdateDTO;
import io.cjf.jinterviewsite.po.Interview;
import io.cjf.jinterviewsite.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping("/getById")
    public JSONObject getById(@RequestParam Integer interviewId){
        return null;
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

    @PostMapping("/update")
    public void update(@RequestBody InterviewUpdateDTO interviewUpdateDTO){
        final Interview interview = interviewService.selectByPrimaryKey(interviewUpdateDTO.getInterviewId());
        interview.setNote(interviewUpdateDTO.getNote());
        interview.setStars(interviewUpdateDTO.getStars());
        interview.setStatus(interviewUpdateDTO.getStatus());
        interview.setInterviewTime(interviewUpdateDTO.getTime());
        interview.setInterviewId(interviewUpdateDTO.getInterviewId());

        interviewService.updateByPrimaryKey(interview);
    }


}
