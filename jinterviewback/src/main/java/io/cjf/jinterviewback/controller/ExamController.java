package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.dto.ExaminationExamByIdDTO;
import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exam")
public class ExamController {


    @Autowired
    private ExaminationService examinationService;

    @GetMapping("/search")
    public List<JSONObject> search(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) Long time){
        System.out.println(keyword);
        System.out.println(time);
        List<ExaminationSearchDTO> list= examinationService.search(keyword,time+50);
        for (ExaminationSearchDTO s:list) {
            System.out.println(s);
        }
        List<JSONObject> exams = list.stream().map(exam -> {
            JSONObject blockJson = new JSONObject();
            blockJson.put("realname", exam.getRealname());
            blockJson.put("examId", exam.getExamId());
            blockJson.put("company", exam.getCompany());
            blockJson.put("time", exam.getTime());
            blockJson.put("likes", exam.getLikes());
            return blockJson;
        }).collect(Collectors.toList());
        return exams ;
    }

    @GetMapping("/getExamById")
    public ExaminationExamByIdDTO getExamById(@RequestParam Integer examId){
        ExaminationExamByIdDTO examinationExamByIdDTO= examinationService.getExamById(examId);

        System.out.println(examinationExamByIdDTO);

        return examinationExamByIdDTO;
    }

}
