package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.BaiduAIService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import io.cjf.jinterviewback.util.SMSUtil;
import io.cjf.jinterviewback.vo.StudentLoginVO;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/student")
public class StudentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.valid.duration}")
    private Long expire_in;

    @Autowired
    private SMSUtil smsUtil;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BaiduAIService baiduAIService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RandomUtil randomUtil;

    @Autowired
    private MailUtil mailUtil;

    private Map<String, String> studentCaptchaMap = new HashMap<>();

    @GetMapping("/autoRegisterLogin")
    public JSONObject autoRegisterLogin(@RequestParam String code) throws ClientException {

        final JSONObject userAccessTokenJsonObj = wechatService.getUserAccessToken(code);
        final Integer errcode = userAccessTokenJsonObj.getInteger("errcode");
        if (errcode != null && errcode != 0) {
            final String errmsg = userAccessTokenJsonObj.getString("errmsg");
            throw new ClientException(ClientExceptionConstant.AUTHCODE_INVALID_ERRCODE, errmsg);
        }
        final String access_token = userAccessTokenJsonObj.getString("access_token");
        final JSONObject userInfoJsonObj = wechatService.getUserInfo(access_token);
        final String nickname = userInfoJsonObj.getString("nickname");
        final String headimgurl = userInfoJsonObj.getString("headimgurl");
        final Byte sex = userInfoJsonObj.getByte("sex");

        final String openid = userInfoJsonObj.getString("openid");
        if (openid == null) {
            throw new ClientException(ClientExceptionConstant.OPENID_NOT_EXIST_ERRCODE, ClientExceptionConstant.OPENID_NOT_EXIST_ERRMSG);
        }

        Student student = studentService.getByOpenid(openid);
        if (student == null) {
            student = new Student();
            student.setOpenid(openid);
            student.setNickname(nickname);
            student.setAvatarUrl(headimgurl);
            student.setGender(sex);
            studentService.createStudent(student);
        } else {
            student.setNickname(nickname);
            student.setAvatarUrl(headimgurl);
            student.setGender(sex);
            studentService.updateStudent(student);
        }

        final String token = jwtUtil.issueToken(student);
        final Byte status = student.getStatus();

        final JSONObject tokenObj = new JSONObject();
        tokenObj.put("token", token);
        tokenObj.put("expire_in", expire_in);
        tokenObj.put("status", status);

        return tokenObj;
    }

    @GetMapping("/getMobileCaptcha")
    public void getMobileCaptcha(@RequestAttribute Integer studentId) throws ClientException, com.aliyuncs.exceptions.ClientException {

        Student student = studentService.getByStudentId(studentId);

        String mobile = student.getMobile();
        if (mobile == null || mobile.isEmpty()) {
            throw new ClientException(ClientExceptionConstant.MOBILE_NOT_EXIST_ERRCODE, ClientExceptionConstant.MOBILE_NOT_EXIST_ERRMSG);
        } else {
            final String captcha = randomUtil.getRandomStr();
            smsUtil.sms(mobile, captcha);
            logger.info("mobile captcha: {}, {}", mobile, captcha);
            studentCaptchaMap.put(CacheKeyConstant.STUDENT_MOBILE_CAPTCHA, captcha);
        }
    }

    @GetMapping("/submitMobileCaptcha")
    public void submitMobileCaptcha(@RequestParam String captcha, @RequestAttribute Integer studentId) throws ClientException {

        final String captchaOirigin = studentCaptchaMap.get(CacheKeyConstant.STUDENT_MOBILE_CAPTCHA);

        if (!captcha.equalsIgnoreCase(captchaOirigin)) {
            throw new ClientException(ClientExceptionConstant.CAPTCHA_INVALID_ERRCODE, ClientExceptionConstant.CAPTCHA_INVALID_ERRMSG);
        } else {
            studentService.activateStudent(studentId);
        }

    }

    @GetMapping("/getBasicInfo")
    public JSONObject getBasicInfo(@RequestParam Integer studentId) {
        JSONObject studentJson = new JSONObject();
        Student student = studentService.getByStudentId(studentId);
        studentJson.put("studentId", student.getStudentId());
        studentJson.put("nickname", student.getNickname());
        studentJson.put("realname", student.getRealname());
        studentJson.put("mobile", student.getMobile());
        studentJson.put("email", student.getEmail());
        studentJson.put("avatarUrl", student.getAvatarUrl());
        return studentJson;
    }
    //

    @GetMapping("/getMailCaptcha")
    public void getMailCaptcha(@RequestAttribute Integer studentId) throws ClientException, GeneralSecurityException, MessagingException {

        Student student = studentService.getByStudentId(studentId);

        String email = student.getEmail();
        if (email == null || email.isEmpty()) {
            throw new ClientException(ClientExceptionConstant.EMail_NOT_EXIST_ERRCODE, ClientExceptionConstant.EMail_NOT_EXIST_ERRMSG);
        } else {
            final String captcha = randomUtil.getRandomStr();
            mailUtil.mailSend(email, captcha);
            logger.info("email captcha: {}, {}", email, captcha);
            studentCaptchaMap.put(CacheKeyConstant.STUDENT_EMAIL_CAPTCHA, captcha);
        }
    }


    @GetMapping("/submitMailCaptcha")
    public void submitMailCaptcha(@RequestParam String captcha, @RequestAttribute Integer studentId) throws ClientException {

        final String captchaOirigin = studentCaptchaMap.get(CacheKeyConstant.STUDENT_EMAIL_CAPTCHA);

        if (!captcha.equalsIgnoreCase(captchaOirigin)) {
            throw new ClientException(ClientExceptionConstant.CAPTCHA_INVALID_ERRCODE, ClientExceptionConstant.CAPTCHA_INVALID_ERRMSG);
        } else {
            final Student student = studentService.getByStudentId(studentId);
            student.setMobileVerified(true);
            studentService.updateStudent(student);
        }

    }

    @PostMapping("/submitIdcard")
    public void submitIdcard(@RequestPart MultipartFile photo, @RequestAttribute Integer studentId) throws IOException {
        final byte[] bytes = photo.getBytes();
        final String photoBase64 = Base64Utils.encodeToString(bytes);
        final JSONObject jsonObject = baiduAIService.ocrIdcard(photoBase64);
        final JSONObject words_result = jsonObject.getJSONObject("words_result");
        final JSONObject nameObj = words_result.getJSONObject("姓名");
        final String name = nameObj.getString("words");

        final Student student = studentService.getByStudentId(studentId);
        student.setRealname(name);
        studentService.updateStudent(student);
    }

}
