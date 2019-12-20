package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.component.WechatParam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WechatServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String code;

    private String accessToken;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WechatParam wechatParam;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUserAccessToken() {
        JSONObject naccessToken = wechatService.getUserAccessToken(code);
        assertNotNull(accessToken);
        logger.info("access token: {}", accessToken);
    }

    @Test
    void getUserInfo(){
        assertNotNull(accessToken);
        final JSONObject userInfoJsonObj = wechatService.getUserInfo(accessToken);
        assertNotNull(userInfoJsonObj);
        final String openid = userInfoJsonObj.getString("openid");
        assertNotNull(openid);
    }

    @Test
    void getAppAccessToken(){
        final JSONObject appAccessToken = wechatService.getAppAccessToken();
        final String access_token = appAccessToken.getString("access_token");
        assertNotNull(access_token);
        final Long expires_in = appAccessToken.getLong("expires_in");
        assertTrue(expires_in != 0);
    }

    @Test
    void sendInterviewNotification(){
        String openid = "oUwXe58JsPM6MBFsI3YvnbFIpg-8";
        String company = "微软";
        Date time = new Date();
        String address = "紫竹科技园区";

        final Long msgId = wechatService.sendInterviewNotification(openid, company, address, time);
        assertTrue( msgId != 0);
    }
}