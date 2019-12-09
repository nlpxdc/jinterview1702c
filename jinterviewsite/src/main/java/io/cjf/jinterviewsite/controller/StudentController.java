package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.client.WechatService;
import io.cjf.jinterviewsite.constant.WechatExceptionConstant;
import io.cjf.jinterviewsite.exception.ClientException;
import io.cjf.jinterviewsite.po.Student;
import io.cjf.jinterviewsite.service.StudentService;
import io.cjf.jinterviewsite.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatService wechatService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/autoRegisterLogin")
    public String autoRegisterLogin(@RequestParam String code) throws ClientException {
        logger.info("code: {}", code);

        final JSONObject userAccessTokenJsonObj = wechatService.getUserAccessToken(code);
        final String access_token = userAccessTokenJsonObj.getString("access_token");
        final JSONObject userInfoJsonObj = wechatService.getUserInfo(access_token);
        final String nickname = userInfoJsonObj.getString("nickname");
        final String headimgurl = userInfoJsonObj.getString("headimgurl");
        final Byte sex = userInfoJsonObj.getByte("sex");

        final String openid = userInfoJsonObj.getString("openid");
        if (openid == null){
            throw new ClientException(WechatExceptionConstant.OPENID_NOT_EXIST_ERRCODE, WechatExceptionConstant.OPENID_NOT_EXIST_ERRMSG);
        }

        final Student originStudent = studentService.getByOpenid(openid);
        Integer studentId;
        if (originStudent == null){
            final Student newStudent = new Student();
            newStudent.setOpenid(openid);
            newStudent.setNickname(nickname);
            newStudent.setAvatarUrl(headimgurl);
            newStudent.setGender(sex);
            studentService.createStudent(newStudent);
            studentId = newStudent.getStudentId();
        }else {
            originStudent.setNickname(nickname);
            originStudent.setAvatarUrl(headimgurl);
            originStudent.setGender(sex);
            studentService.updateStudent(originStudent);
            studentId = originStudent.getStudentId();
        }

        final String token = jwtUtil.issueToken(studentId, openid);

        return token;
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
