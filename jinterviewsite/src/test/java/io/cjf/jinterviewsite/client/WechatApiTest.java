package io.cjf.jinterviewsite.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WechatApiTest {

    @Autowired
    private WechatApi wechatApi;

    private String appid = "wx0c14a6dfeab19166";
    private String secret = "02a53bc213a98bb9fa6aae4157fd55eb";;
    private String code;
    private String grant_type = "authorization_code";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUserAccessToken() {
        final String userAccessTokenJsonStr = wechatApi.getUserAccessToken(appid, secret, code, grant_type);
        assertNotNull(userAccessTokenJsonStr);
        final JSONObject userAccessTokenJsonObj = JSON.parseObject(userAccessTokenJsonStr);
        assertNotNull(userAccessTokenJsonObj);
        final String access_token = userAccessTokenJsonObj.getString("access_token");
        assertNotNull(access_token);
    }
}