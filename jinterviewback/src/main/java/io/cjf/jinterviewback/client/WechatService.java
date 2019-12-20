package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface WechatService {

    JSONObject getUserAccessToken(String code);

    JSONObject getUserInfo(String accessToken);

    JSONObject getAppAccessToken();

    Long sendInterviewNotification(String openid, String company, String address, Date time);



}
