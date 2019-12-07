package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.client.WechatService;
import io.cjf.jinterviewsite.constant.WechatExceptionConstant;
import io.cjf.jinterviewsite.exception.ClientException;
import io.cjf.jinterviewsite.po.Student;
import io.cjf.jinterviewsite.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.event.CaretListener;

@RestController
@RequestMapping("/student")
public class StudentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatService wechatService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/autoRegisterLogin")
    public String autoRegisterLogin(@RequestParam String code) throws ClientException {
        logger.info("code: {}", code);

        final String userAccessToken = wechatService.getUserAccessToken(code);
        final JSONObject userInfoJsonObj = wechatService.getUserInfo(userAccessToken);

        final String openid = userInfoJsonObj.getString("openid");
        if (openid == null){
            throw new ClientException(WechatExceptionConstant.OPENID_NOT_EXIST_ERRCODE, WechatExceptionConstant.OPENID_NOT_EXIST_ERRMSG);
        }

        final Student student = studentService.getByOpenid(openid);
        //todo not exist, insert
        //todo login, generate token

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
