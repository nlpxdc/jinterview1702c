package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.component.WechatParam;
import io.cjf.jinterviewback.dto.WechatTemplateMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Override
    public JSONObject getUserAccessToken(String code) {
        final String userAccessTokenJsonStr = wechatApi.getUserAccessToken(appid, secret, code, "authorization_code");
        final JSONObject userAccessTokenJsonObj = JSON.parseObject(userAccessTokenJsonStr);
        return userAccessTokenJsonObj;
    }

    @Override
    public JSONObject getAppAccessToken() {
        JSONObject appAccessTokenObj = wechatApi.getAppAccessToken(appid, secret, "client_credential");
        return appAccessTokenObj;
    }

    @Override
    public Long sendTemplateMessage(WechatTemplateMessageDTO templateMessageDTO) {
        JSONObject templateMessageObj = wechatApi.sendTemplateMessage(wechatParam.getAppAccessToken(), templateMessageDTO);
        final Long msgid = templateMessageObj.getLong("msgid");
        return msgid;
    }

    @Override
    public JSONObject getUserInfo(String accessToken) {
        final String userInfoJsonStr = wechatApi.getUserInfo(accessToken, "myopenId", "zh_CN");
        final JSONObject userInfoJsonObj = JSON.parseObject(userInfoJsonStr);
        return userInfoJsonObj;
    }
}
