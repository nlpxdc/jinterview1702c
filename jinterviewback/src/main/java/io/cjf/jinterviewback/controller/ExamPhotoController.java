package io.cjf.jinterviewback.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/examphoto")
@CrossOrigin
public class ExamPhotoController {

    @PostMapping("/upload")
    public List<String> upload(@RequestParam("examphotos") MultipartFile[] photos,
                               @RequestParam Integer interviewId){
        return null;
    }
}
