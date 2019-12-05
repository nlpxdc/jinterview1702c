package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/autoRegisterLogin")
    public Integer autoRegisterLogin(@RequestParam String code){
        return null;
    }

    @GetMapping("/getMobileCaptcha")
    public void getMobileCaptcha(){

    }

    @GetMapping("/submitMobileCaptcha")
    public void submitMobileCaptcha(@RequestParam String captcha){

    }

    @GetMapping("/getBasicInfo")
    public JSONObject getBasicInfo(){
        return null;
    }
}
