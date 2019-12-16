package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.WechatService;
import io.cjf.jinterviewback.constant.CacheKeyConstant;
import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.exception.ClientException;
import io.cjf.jinterviewback.po.Student;
import io.cjf.jinterviewback.service.StudentService;
import io.cjf.jinterviewback.util.JWTUtil;
import io.cjf.jinterviewback.util.MailUtil;
import io.cjf.jinterviewback.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.cjf.jinterviewback.util.SMSUtil;
import io.cjf.jinterviewback.vo.StudentLoginVO;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/student")
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

    @Autowired
    private MailUtil mailUtil;

    private Map<String, String> studentCaptchaMap = new HashMap<>();

    @GetMapping("/autoRegisterLogin")
    public String autoRegisterLogin(@RequestParam String code) throws ClientException {

        final JSONObject userAccessTokenJsonObj = wechatService.getUserAccessToken(code);
        final Integer errcode = userAccessTokenJsonObj.getInteger("errcode");
        if (errcode != null && errcode != 0){
            final String errmsg = userAccessTokenJsonObj.getString("errmsg");
            throw new ClientException(ClientExceptionConstant.AUTHCODE_INVALID_ERRCODE, errmsg);
        }
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

        Student student = studentService.getByStudentId(studentId);

        String mobile = student.getMobile();
        if (mobile == null || mobile.isEmpty()){
            throw new ClientException(ClientExceptionConstant.MOBILE_NOT_EXIST_ERRCODE, ClientExceptionConstant.MOBILE_NOT_EXIST_ERRMSG);
        }else {
            final String captcha = randomUtil.getRandomStr();
            smsUtil.sms(mobile, captcha);
            logger.info("mobile captcha: {}, {}", mobile, captcha);
            studentCaptchaMap.put(CacheKeyConstant.STUDENT_MOBILE_CAPTCHA, captcha);
        }
    }

    @GetMapping("/submitMobileCaptcha")
    public void submitMobileCaptcha(@RequestParam String captcha, @RequestAttribute Integer studentId) throws ClientException {

        final String captchaOirigin = studentCaptchaMap.get(CacheKeyConstant.STUDENT_MOBILE_CAPTCHA);

        if(!captcha.equalsIgnoreCase(captchaOirigin)){
            throw new ClientException(ClientExceptionConstant.CAPTCHA_INVALID_ERRCODE, ClientExceptionConstant.CAPTCHA_INVALID_ERRMSG);
        }else {
            studentService.activateStudent(studentId);
        }

    }

    @GetMapping("/getBasicInfo")
    public JSONObject getBasicInfo(@RequestParam Integer studentId){
        JSONObject studentJson = new JSONObject();
        Student student = studentService.getByStudentId(studentId);
        studentJson.put("studentId",student.getStudentId());
        studentJson.put("nickname",student.getNickname());
        studentJson.put("realname",student.getRealname());
        studentJson.put("mobile",student.getMobile());
        studentJson.put("email",student.getEmail());
        studentJson.put("avatarUrl",student.getAvatarUrl());
        return studentJson;
    }
    //

    @GetMapping("/getMailCaptcha")
    public void getMailCaptcha(@RequestAttribute Integer studentId) throws ClientException, GeneralSecurityException, MessagingException {

        Student student = studentService.getByStudentId(studentId);

        String email = student.getEmail();
        if (email == null || email.isEmpty()){
            throw new ClientException(ClientExceptionConstant.EMail_NOT_EXIST_ERRCODE, ClientExceptionConstant.EMail_NOT_EXIST_ERRMSG);
        }else {
            final String captcha = randomUtil.getRandomStr();
            mailUtil.mailSend(email,captcha);
            logger.info("email captcha: {}, {}", email, captcha);
            studentCaptchaMap.put(CacheKeyConstant.STUDENT_EMAIL_CAPTCHA, captcha);
        }
    }


    @GetMapping("/submitMailCaptcha")
    public void submitMailCaptcha(@RequestParam String captcha, @RequestAttribute Integer studentId) throws ClientException {

        final String captchaOirigin = studentCaptchaMap.get(CacheKeyConstant.STUDENT_EMAIL_CAPTCHA);

        if(!captcha.equalsIgnoreCase(captchaOirigin)){
            throw new ClientException(ClientExceptionConstant.CAPTCHA_INVALID_ERRCODE, ClientExceptionConstant.CAPTCHA_INVALID_ERRMSG);
        }else {
            final Student student = studentService.getByStudentId(studentId);
            student.setMobileVerified(true);
            studentService.updateStudent(student);
        }

    }

}
