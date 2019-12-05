package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.dto.InterviewCreateDTO;
import io.cjf.jinterviewsite.dto.InterviewUpdateDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview")
public class InterviewController {

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

    }


}
