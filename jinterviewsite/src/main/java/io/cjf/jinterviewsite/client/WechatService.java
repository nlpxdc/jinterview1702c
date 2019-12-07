package io.cjf.jinterviewsite.client;

import com.alibaba.fastjson.JSONObject;

public interface WechatService {

    String getUserAccessToken(String code);

    JSONObject getUserInfo(String accessToken);

}
