package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.po.AudioRecord;
import io.cjf.jinterviewsite.service.AudioRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/audiorecord")
public class AudioRecordController {

    @Autowired
    private AudioRecordService audioRecordService;

    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] audiorecords,
                               @RequestParam Integer interviewId){
        return null;
    }

    @GetMapping("/search")
    public List<JSONObject> search(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) Long time){

        List<AudioRecord> list=audioRecordService.search(keyword,time);
        List<JSONObject> audio=list.stream().map(t->{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("audiorecordId",t.getAudioRecordId());
            jsonObject.put("title",t.getTitle());
            jsonObject.put("content",t.getContent());
            jsonObject.put("likes",t.getLikes());
            jsonObject.put("audiorecordUrl",t.getUrl());


                return jsonObject;

        }).collect(Collectors.toList());

        return audio;
    }

    @GetMapping("/getById")
    public JSONObject getById(@RequestParam Integer audiorecordId){

        JSONObject jsonObject = new JSONObject();
        AudioRecord audioRecord=audioRecordService.getByid(audiorecordId);

        jsonObject.put("audiorecordId",audioRecord.getAudioRecordId());
        jsonObject.put("title",audioRecord.getTitle());
        jsonObject.put("content",audioRecord.getContent());
        jsonObject.put("likes",audioRecord.getLikes());
        jsonObject.put("audiorecordUrl",audioRecord.getUrl());


        return jsonObject;
    }
}
