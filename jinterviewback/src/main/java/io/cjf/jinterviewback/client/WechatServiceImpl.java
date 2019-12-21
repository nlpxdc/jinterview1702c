package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    private String appAccessToken;
    private Long expireTimestamp;

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
        return appAccessTokenObj;
    }

    @Override
    public Long sendTemplateMessage(WechatTemplateMessageDTO templateMessageDTO) {
        final String appAccessToken = autoGetAppAccessToken();
        final JSONObject jsonObject = wechatApi.sendTemplateMessage(appAccessToken, templateMessageDTO);
        final Long msgid = jsonObject.getLong("msgid");
        return msgid;
    }

    private String autoGetAppAccessToken() {
        final Date now = new Date();
        final long nowTimestamp = now.getTime();
        if (appAccessToken == null || nowTimestamp > expireTimestamp) {
            //todo sync token
            final JSONObject appAccessTokenObj = getAppAccessToken();
            appAccessToken = appAccessTokenObj.getString("access_token");
            final Long expires_in = appAccessTokenObj.getLong("expires_in");
            expireTimestamp = nowTimestamp + expires_in * 1000;
        }
        return appAccessToken;
    }

}
