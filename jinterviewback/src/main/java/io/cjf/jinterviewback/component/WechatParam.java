package io.cjf.jinterviewback.component;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WechatParam {

    @Autowired
    private WechatService wechatService;

    private String appAccessToken;
    private Long expireTimestamp;

    public String getAppAccessToken() {
        final Date now = new Date();
        final long nowTimestamp = now.getTime();
        if (appAccessToken == null || nowTimestamp > expireTimestamp) {
            final JSONObject appAccessTokenObj = wechatService.getAppAccessToken();
            appAccessToken = appAccessTokenObj.getString("access_token");
            final Long expires_in = appAccessTokenObj.getLong("expires_in");
            expireTimestamp = nowTimestamp + expires_in * 1000;
        }
        return appAccessToken;
    }

}
