package io.cjf.jinterviewsite.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WechatApiTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatApi wechatApi;

    private String appid = "wx0c14a6dfeab19166";
    private String secret = "02a53bc213a98bb9fa6aae4157fd55eb";;
    private String code;
    private String grant_type = "authorization_code";
    private String access_token = "28_E7LRPOxOIxb9O6yqS4q_-qIG6hGz97epnTbXHuKaA1w52FfUkpAwc2ll5PsSRXLv_vhjz15J2fE9RREkdgxWRaQKeeQran5BOI4r0OCtnwk";

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
        logger.info("accessToken: {}", access_token);
    }

    @Test
    void getUserInfo(){
        final String userInfoJsonStr = wechatApi.getUserInfo(access_token, "myopenid", "zh_CN");
        assertNotNull(userInfoJsonStr);
        final JSONObject userInfoJsonObj = JSON.parseObject(userInfoJsonStr);
        assertNotNull(userInfoJsonObj);
        final String openid = userInfoJsonObj.getString("openid");
        assertNotNull(openid);
    }
}