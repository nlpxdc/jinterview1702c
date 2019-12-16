package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatServiceImpl implements WechatService{

    @Value("${wechat.appId}")
    private String appid;

    @Value("${wechat.appSecret}")
    private String secret;

    private String grant_type = "authorization_code";

    @Autowired
    private WechatApi wechatApi;

    @Override
    public JSONObject getUserAccessToken(String code) {
        final String userAccessTokenJsonStr = wechatApi.getUserAccessToken(appid, secret, code, grant_type);
        final JSONObject userAccessTokenJsonObj = JSON.parseObject(userAccessTokenJsonStr);
        return userAccessTokenJsonObj;
    }

    @Override
    public JSONObject getTemToken() {
        String getTemTokenStr = wechatApi.getTemToken(appid,secret,"client_credential");
        JSONObject getTemTokenObj = JSON.parseObject(getTemTokenStr);
        return getTemTokenObj;
    }

    @Override
    public JSONObject templateMessage(String access_token,JSON jsonData) {
        String templateMessageStr = wechatApi.templateMessage(access_token,jsonData);
        JSONObject templateMessageObj = JSON.parseObject(templateMessageStr);
        return templateMessageObj;
    }

    @Override
    public JSONObject getUserInfo(String accessToken) {
        final String userInfoJsonStr = wechatApi.getUserInfo(accessToken, "myopenId", "zh_CN");
        final JSONObject userInfoJsonObj = JSON.parseObject(userInfoJsonStr);
        return userInfoJsonObj;
    }
}
