package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chart")
public class ChartController {

    @GetMapping("/getStudentInterview")
    public List<JSONObject> getStudentInterview(){
        return null;
    }

}
