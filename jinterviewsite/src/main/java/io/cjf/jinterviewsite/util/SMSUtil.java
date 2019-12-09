package io.cjf.jinterviewsite.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

@Component
public class SMSUtil {

    public String sms(String mobile){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FdgpYeTNnTEE5DnUvnz", "sfMHRwUUjGG1cHRXZn7k9y2SPDVaZ4");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");

        request.putQueryParameter("SignName", "录音程序");
        request.putQueryParameter("TemplateCode", "SMS_179601056");


        request.putQueryParameter("PhoneNumbers",mobile);
        //        String code = RandomStringUtils.randomNumeric(4);
       //        System.out.println(code);
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = secureRandom.generateSeed(2);
        String code = DatatypeConverter.printHexBinary(bytes);


        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");

        // request.putQueryParameter("TemplateParam", "{\"code\":\"741\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (
                ClientException e) {
            e.printStackTrace();
        }
       return code;
    }

}
