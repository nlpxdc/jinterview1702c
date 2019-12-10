package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.WechatService;
import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.exception.ClientException;
import io.cjf.jinterviewback.po.Student;
import io.cjf.jinterviewback.service.StudentService;
import io.cjf.jinterviewback.util.JWTUtil;
import io.cjf.jinterviewback.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.cjf.jinterviewback.util.SMSUtil;
import io.cjf.jinterviewback.vo.StudentLoginVO;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SMSUtil smsUtil;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RandomUtil randomUtil;

    private Map<Integer, String> studentCaptchaMap = new HashMap<>();

    @GetMapping("/autoRegisterLogin")
    public String autoRegisterLogin(@RequestParam String code) throws ClientException {

        final JSONObject userAccessTokenJsonObj = wechatService.getUserAccessToken(code);
        final String access_token = userAccessTokenJsonObj.getString("access_token");
        final JSONObject userInfoJsonObj = wechatService.getUserInfo(access_token);
        final String nickname = userInfoJsonObj.getString("nickname");
        final String headimgurl = userInfoJsonObj.getString("headimgurl");
        final Byte sex = userInfoJsonObj.getByte("sex");

        final String openid = userInfoJsonObj.getString("openid");
        if (openid == null){
            throw new ClientException(ClientExceptionConstant.OPENID_NOT_EXIST_ERRCODE, ClientExceptionConstant.OPENID_NOT_EXIST_ERRMSG);
        }

        Student student = studentService.getByOpenid(openid);
        if (student == null){
            student = new Student();
            student.setOpenid(openid);
            student.setNickname(nickname);
            student.setAvatarUrl(headimgurl);
            student.setGender(sex);
            studentService.createStudent(student);
        }else {
            student.setNickname(nickname);
            student.setAvatarUrl(headimgurl);
            student.setGender(sex);
            studentService.updateStudent(student);
        }

        final String token = jwtUtil.issueToken(student);

        return token;
    }

    @GetMapping("/getMobileCaptcha")
    public void getMobileCaptcha(@RequestAttribute Integer studentId) throws ClientException, com.aliyuncs.exceptions.ClientException {

        Student student = studentService.getBystudentId(studentId);

        String mobile = student.getMobile();
        if (mobile == null || mobile.isEmpty()){
            throw new ClientException(ClientExceptionConstant.MOBILE_NOT_EXIST_ERRCODE, ClientExceptionConstant.MOBILE_NOT_EXIST_ERRMSG);
        }else {
            final String captcha = randomUtil.getRandomStr();
            smsUtil.sms(mobile, captcha);
            studentCaptchaMap.put(studentId, captcha);
        }
    }

    @GetMapping("/submitMobileCaptcha")
    public void submitMobileCaptcha(@RequestParam String captcha, @RequestAttribute Integer studentId) throws ClientException {

        final String captchaOirigin = studentCaptchaMap.get(studentId);

        if(!captcha.equalsIgnoreCase(captchaOirigin)){
            throw new ClientException(ClientExceptionConstant.CAPTCHA_INVALID_ERRCODE, ClientExceptionConstant.CAPTCHA_INVALID_ERRMSG);
        }else {
            studentService.activateStudent(studentId);
        }

    }

    @GetMapping("/getBasicInfo")
    public JSONObject getBasicInfo(){
        return null;
    }
}
