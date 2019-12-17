package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.dto.AudioRecordDTO;
import io.cjf.jinterviewback.po.AudioRecord;
import io.cjf.jinterviewback.service.AudioRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/audiorecord")
public class AudioRecordController {
    private HashSet md5Set = new HashSet();
    private static final Logger log = LoggerFactory.getLogger(AudioRecordController.class);
    @Autowired
    private AudioRecordService audioRecordService;

 @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile audiorecords,
                               @RequestParam Integer interviewId) throws IOException {
         AudioRecord audioRecord = new AudioRecord();
         String name = audiorecords.getOriginalFilename();
         String[] split = name.split("\\.");
         String ext = split[split.length-1];
         byte[] data = audiorecords.getBytes();
         String uuid = UUID.randomUUID().toString();
         String filename = String.format("Audio/%s.%s", uuid, ext);
         try(FileOutputStream out = new FileOutputStream(filename)){
             out.write(data);
         }
         String md5HexStr = DigestUtils.md5DigestAsHex(data);
         md5Set.add(md5HexStr);

         audioRecord.setInterviewId(interviewId);
         audioRecord.setUrl(filename);
         audioRecord.setLikes(0);
         audioRecordService.insertAudio(audioRecord);
         List<AudioRecord> audioRecordList=audioRecordService.getAudioByInterviewId(interviewId);
         ArrayList<String> urls = new ArrayList<>();
         for (AudioRecord audio:audioRecordList
         ) {
             urls.add(audio.getUrl());
         }
     return urls;
    }

    @GetMapping("/search")
    public List<JSONObject> search(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) Long time){

        List<AudioRecordDTO> list=audioRecordService.search(keyword,time);
        List<JSONObject> audio=list.stream().map(t->{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("audiorecordId",t.getAudiorecordId());
            jsonObject.put("title",t.getInterviewId());
            jsonObject.put("content",t.getCompany());
            jsonObject.put("likes",t.getLikes());
            jsonObject.put("audiorecordUrl",t.getStudent());


                return jsonObject;

        }).collect(Collectors.toList());

        return audio;
    }

    @GetMapping("/getById")
    public JSONObject getById(@RequestParam Integer audiorecordId){

        JSONObject jsonObject = new JSONObject();
        AudioRecordDTO audioRecord=audioRecordService.getByid(audiorecordId);

        jsonObject.put("audiorecordId",audioRecord.getAudiorecordId());
        jsonObject.put("title",audioRecord.getTitle());
        jsonObject.put("content",audioRecord.getContent());
        jsonObject.put("likes",audioRecord.getLikes());
        jsonObject.put("student",audioRecord.getStudent());
        jsonObject.put("url",audioRecord.getUrl());
        jsonObject.put("status",audioRecord.getStatus());
        jsonObject.put("interview_time",audioRecord.getInterview_time());


        return jsonObject;
    }

}
