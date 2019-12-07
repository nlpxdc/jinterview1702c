package io.cjf.jinterviewsite.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewsite.client.WechatApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private WechatApi wechatApi;

    @GetMapping("/hello")
    public String hello() {

        String appid = "wx0c14a6dfeab19166";
        String secret = "02a53bc213a98bb9fa6aae4157fd55eb";
        String code = "";
        String grant_type = "authorization_code";
        final String userAccessTokenJsonStr = wechatApi.getUserAccessToken(appid, secret, code, grant_type);
        final JSONObject userAccessTokenJsonObj = JSON.parseObject(userAccessTokenJsonStr);
        final String access_token = userAccessTokenJsonObj.getString("access_token");

        return "aaa";
    }
}
