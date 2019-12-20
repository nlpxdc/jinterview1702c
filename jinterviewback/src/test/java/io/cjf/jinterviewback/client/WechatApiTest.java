package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.component.WechatParam;
import io.cjf.jinterviewback.dto.WechatTemplateMessageDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WechatApiTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatApi wechatApi;

    @Value("${wechat.appId}")
    private String appid;

    @Value("${wechat.appSecret}")
    private String secret;

    private String code;

    private String access_token;

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
        final String userAccessTokenJsonStr = wechatApi.getUserAccessToken(appid, secret, code, "authorization_code");
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

    @Test
    void getAppAccessToken(){
        final JSONObject appAccessTokenObj = wechatApi.getAppAccessToken(appid, secret, "client_credential");
        assertNotNull(appAccessTokenObj);
        final String access_token = appAccessTokenObj.getString("access_token");
        assertNotNull(access_token);
        final Long expires_in = appAccessTokenObj.getLong("expires_in");
        assertTrue(expires_in == 7200);
    }

    @Test
    void sendTemplateMessage(){
        String openid = "oUwXe58JsPM6MBFsI3YvnbFIpg-8";
        String templateId = "rfqhowneOh3u7y1JCyKB0NUOMvPUx2FWiao4OSVt9Sw";
        String url = "http://www.baidu.com";
        String company = "微软";
        Date time = new Date();
        String address = "紫竹科技园区";

        final WechatTemplateMessageDTO templateMessageDTO = new WechatTemplateMessageDTO();
        templateMessageDTO.setTouser(openid);
        templateMessageDTO.setTemplateId(templateId);
        templateMessageDTO.setUrl(url);
        final JSONObject dataJson = new JSONObject();

        final JSONObject companyObj = new JSONObject();
        companyObj.put("value", company);
        companyObj.put("color", "#ff0000");
        dataJson.put("company", companyObj);

        final JSONObject timeObj = new JSONObject();
        timeObj.put("value", time.toString());
        timeObj.put("color", "#ff0000");
        dataJson.put("time", timeObj);

        final JSONObject addressObj = new JSONObject();
        addressObj.put("value", address);
        addressObj.put("color", "#ff0000");
        dataJson.put("address", addressObj);

        templateMessageDTO.setData(dataJson);

        final String appAccessToken = wechatParam.getAppAccessToken();
        final JSONObject jsonObject = wechatApi.sendTemplateMessage(appAccessToken, templateMessageDTO);
        final Long msgid = jsonObject.getLong("msgid");
        assertTrue(msgid != 0);
    }
}