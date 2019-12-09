package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.annotation.NotRequiredLogin;
import io.cjf.jinterviewsite.client.WechatService;
import io.cjf.jinterviewsite.constant.ClientExceptionConstant;
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
import io.cjf.jinterviewsite.util.SMSUtil;
import io.cjf.jinterviewsite.vo.StudentLoginVO;



@RestController
@RequestMapping("/student")
public class StudentController {

    private String verification;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SMSUtil smsUtil;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JWTUtil jwtUtil;

    @NotRequiredLogin
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
    public void getMobileCaptcha(@RequestParam String token){
        System.out.println("获取验证码");
        System.out.println(token);



        StudentLoginVO studentLoginVO = jwtUtil.verifyToken(token);
        Integer studentId = studentLoginVO.getStudentId();
        System.out.println(studentId);
        Student student = studentService.getBystudentId(studentId);
        String mobile = student.getMobile();

        String sms = smsUtil.sms(mobile);
        verification=sms;





    }

    @GetMapping("/submitMobileCaptcha")
    public boolean submitMobileCaptcha(@RequestParam String captcha,@RequestParam String token){
        System.out.println("激活");

        StudentLoginVO studentLoginVO = jwtUtil.verifyToken(token);
        Integer studentId = studentLoginVO.getStudentId();
        System.out.println(captcha);
        System.out.println(verification);
        if(verification.equals(captcha)){

            studentService.updateStatus(studentId);

            return true;
        }else{
            return false;
        }

    }

    @GetMapping("/getBasicInfo")
    public JSONObject getBasicInfo(){
        return null;
    }
}
