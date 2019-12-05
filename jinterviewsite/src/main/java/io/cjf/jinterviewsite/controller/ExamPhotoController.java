package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/examphoto")
public class ExamPhotoController {

    @PostMapping("/upload")
    public List<String> upload(@RequestParam("examphotos") MultipartFile[] photos,
                               @RequestParam Integer interviewId){
        return null;
    }
}
