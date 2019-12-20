package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.component.WechatParam;
import io.cjf.jinterviewback.dto.WechatTemplateMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WechatServiceImpl implements WechatService {

    @Value("${wechat.appId}")
    private String appid;

    @Value("${wechat.appSecret}")
    private String secret;

    @Autowired
    private WechatApi wechatApi;

    @Autowired
    private WechatParam wechatParam;

    @Value("${interview.notification.templateId}")
    private String templateId;

    @Value("${interview.notification.url}")
    private String interviewUrl;

    @Override
    public JSONObject getUserAccessToken(String code) {
        final String userAccessTokenJsonStr = wechatApi.getUserAccessToken(appid, secret, code, "authorization_code");
        final JSONObject userAccessTokenJsonObj = JSON.parseObject(userAccessTokenJsonStr);
        return userAccessTokenJsonObj;
    }

    @Override
    public JSONObject getUserInfo(String accessToken) {
        final String userInfoJsonStr = wechatApi.getUserInfo(accessToken, "myopenId", "zh_CN");
        final JSONObject userInfoJsonObj = JSON.parseObject(userInfoJsonStr);
        return userInfoJsonObj;
    }

    @Override
    public JSONObject getAppAccessToken() {
        JSONObject appAccessTokenObj = wechatApi.getAppAccessToken(appid, secret, "client_credential");
        final String access_token = appAccessTokenObj.getString("access_token");
        wechatParam.setAppAccessToken(access_token);
        return appAccessTokenObj;
    }

    @Override
    public Long sendInterviewNotification(String openid, String company, String address, Date time) {

        final WechatTemplateMessageDTO templateMessageDTO = new WechatTemplateMessageDTO();
        templateMessageDTO.setTouser(openid);
        templateMessageDTO.setTemplateId(templateId);
        templateMessageDTO.setUrl(interviewUrl);
        final JSONObject dataJson = new JSONObject();

        final JSONObject companyObj = new JSONObject();
        companyObj.put("value", company);
        companyObj.put("color", "#ff0000");
        dataJson.put("company", companyObj);

        final JSONObject timeObj = new JSONObject();
        timeObj.put("value", time.toString());
        timeObj.put("color", "#ff0000");
        dataJson.put("time", timeObj);

        final JSONObject addressObj = new JSONObject();
        addressObj.put("value", address);
        addressObj.put("color", "#ff0000");
        dataJson.put("address", addressObj);

        templateMessageDTO.setData(dataJson);

        final String appAccessToken = wechatParam.getAppAccessToken();
        final JSONObject jsonObject = wechatApi.sendTemplateMessage(appAccessToken, templateMessageDTO);
        final Long msgid = jsonObject.getLong("msgid");

        return msgid;
    }


}
