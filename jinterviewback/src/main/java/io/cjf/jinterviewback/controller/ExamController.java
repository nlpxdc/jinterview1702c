package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.dto.ExamShowDTO;
import io.cjf.jinterviewback.dto.ExaminationSearchDTO;
import io.cjf.jinterviewback.po.ExamPhoto;
import io.cjf.jinterviewback.service.ExaminationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exam")
public class ExamController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExaminationService examinationService;

    @Value("${exam.photo.url.prefix}")
    private String examPhotoUrlPrefix;

    @Value("${exam.photo.directory}")
    private String examPhotoDirectory;

    @GetMapping("/search")
    public List<JSONObject> search(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) Long time){
        logger.info("keyword"+keyword);
        logger.info("time"+time);
        Date date = new Date(time);
        List<ExaminationSearchDTO> list= examinationService.search(keyword,date);
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
    public ExamShowDTO getExamById(@RequestParam Integer examId){
        ExamShowDTO examShowDTO = examinationService.getExamById(examId);
        final List<ExamPhoto> examPhotos = examShowDTO.getExamPhotos();
        final List<String> examPhotoUrls = examPhotos.stream().map(examPhoto -> String.format("%s%s/%s", examPhotoUrlPrefix, examPhotoDirectory, examPhoto.getUrl())).collect(Collectors.toList());
        examShowDTO.setExamPhotoUrls(examPhotoUrls);
        return examShowDTO;
    }

}
