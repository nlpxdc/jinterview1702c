package io.cjf.jinterviewback.component;

import org.springframework.stereotype.Component;

@Component
public class WechatParam {

    private String appAccessToken;

    public String getAppAccessToken() {
        return appAccessToken;
    }

    public void setAppAccessToken(String appAccessToken) {
        this.appAccessToken = appAccessToken;
    }
}
