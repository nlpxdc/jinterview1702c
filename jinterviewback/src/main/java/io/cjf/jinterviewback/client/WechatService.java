package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public interface WechatService {

    JSONObject getUserAccessToken(String code);

    JSONObject getTemToken();

    JSONObject templateMessage(String access_token, JSON jsonData);

    JSONObject getUserInfo(String accessToken);

}
