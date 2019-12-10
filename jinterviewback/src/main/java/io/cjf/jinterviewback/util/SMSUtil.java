package io.cjf.jinterviewback.util;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Component;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

@Component
public class SMSUtil {

    public void sms(String mobile, String content) throws ClientException {
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

        request.putQueryParameter("TemplateParam", "{\"code\":\""+content+"\"}");


        CommonResponse response = client.getCommonResponse(request);

    }

}
