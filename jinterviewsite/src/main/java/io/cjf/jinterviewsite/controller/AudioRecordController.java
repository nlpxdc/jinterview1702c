package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/audiorecord")
public class AudioRecordController {

    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] audiorecords,
                               @RequestParam Integer interviewId){
        return null;
    }

    @GetMapping("/search")
    public List<JSONObject> search(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) Long time){
        return null;
    }

    @GetMapping("/getById")
    public JSONObject getById(@RequestParam Integer audiorecordId){
        return null;
    }
}
