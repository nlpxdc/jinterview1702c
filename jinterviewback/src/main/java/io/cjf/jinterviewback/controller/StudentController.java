package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.BaiduAIService;
import io.cjf.jinterviewback.client.WechatService;
import io.cjf.jinterviewback.constant.CacheKeyConstant;
import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.exception.ClientException;
import io.cjf.jinterviewback.po.Student;
import io.cjf.jinterviewback.service.StudentService;
import io.cjf.jinterviewback.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import io.cjf.jinterviewback.vo.StudentLoginVO;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
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
        logger.info("auth code: {}", code);

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

        final JSONObject tokenObj = jwtUtil.issueToken(student);
        final Byte status = student.getStatus();
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
            String key = CacheKeyConstant.STUDENT_MOBILE_CAPTCHA + studentId;
            studentCaptchaMap.put(key, captcha);
        }
    }

    @GetMapping("/submitMobileCaptcha")
    public void submitMobileCaptcha(@RequestParam String captcha, @RequestAttribute Integer studentId) throws ClientException {

        String key = CacheKeyConstant.STUDENT_MOBILE_CAPTCHA + studentId;
        final String captchaOirigin = studentCaptchaMap.get(key);

        if (!captcha.equalsIgnoreCase(captchaOirigin)) {
            throw new ClientException(ClientExceptionConstant.CAPTCHA_INVALID_ERRCODE, ClientExceptionConstant.CAPTCHA_INVALID_ERRMSG);
        } else {
            studentService.activateStudent(studentId);
        }

    }

    @GetMapping("/getBasicInfo")
    public JSONObject getBasicInfo(@RequestAttribute Integer studentId) {
        JSONObject studentJson = new JSONObject();
        Student student = studentService.getByStudentId(studentId);
        studentJson.put("studentId", student.getStudentId());
        studentJson.put("nickname", student.getNickname());
        studentJson.put("realname", student.getRealname());
        studentJson.put("mobile", student.getMobile());
        studentJson.put("email", student.getEmail());
        studentJson.put("emailVerified", student.getEmailVerified());
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
//            mailUtil.mailSend(email, captcha);
            mailUtil.mailSend2(email, "面试系统邮箱验证码", captcha);

            logger.info("email captcha: {}, {}", email, captcha);
            String key = CacheKeyConstant.STUDENT_EMAIL_CAPTCHA + studentId;
            studentCaptchaMap.put(key, captcha);
        }
    }


    @GetMapping("/submitMailCaptcha")
    public void submitMailCaptcha(@RequestParam String captcha, @RequestAttribute Integer studentId) throws ClientException {

        String key = CacheKeyConstant.STUDENT_EMAIL_CAPTCHA + studentId;
        final String captchaOirigin = studentCaptchaMap.get(key);

        if (!captcha.equalsIgnoreCase(captchaOirigin)) {
            throw new ClientException(ClientExceptionConstant.CAPTCHA_INVALID_ERRCODE, ClientExceptionConstant.CAPTCHA_INVALID_ERRMSG);
        } else {
            final Student student = studentService.getByStudentId(studentId);
            student.setEmailVerified(true);
            studentService.updateStudent(student);
        }

    }

    @PostMapping("/submitIdcard")
    public String submitIdcard(@RequestPart MultipartFile Idcard, @RequestAttribute Integer studentId) throws IOException, ClientException {
        final String contentType = Idcard.getContentType();
        if (!contentType.equals(MediaType.IMAGE_JPEG_VALUE)){
            throw new ClientException(ClientExceptionConstant.NOT_JPEG_FORMAT_ERRCODE, ClientExceptionConstant.NOT_JPEG_FORMAT_ERRMSG);
        }

        final long size = Idcard.getSize();
        if (size > 100 * 1024){
            throw new ClientException(ClientExceptionConstant.IDCARD_TOO_LARGE_ERRCODE, ClientExceptionConstant.IDCARD_TOO_LARGE_ERRMSG);
        }

        final InputStream idcardInputStream = Idcard.getInputStream();
        final byte[] data = ImgUtil.redraw(idcardInputStream, 360);

        final JSONObject jsonObject = baiduAIService.ocrIdcard(data);
        final JSONObject words_result = jsonObject.getJSONObject("words_result");
        final JSONObject nameObj = words_result.getJSONObject("姓名");
        final String name = nameObj.getString("words");

        final Student student = studentService.getByStudentId(studentId);
        student.setRealname(name);
        studentService.updateStudent(student);

        return name;
    }

}
