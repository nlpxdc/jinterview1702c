package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaiduAIApiTest {

    @Autowired
    private BaiduAIApi baiduAIApi;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAppAccessToken() {
        String apiKey = "";
        String secret = "";
        final JSONObject jsonObject = baiduAIApi.getAppAccessToken("client_credentials", apiKey, secret);
        assertNotNull(jsonObject);
        final String access_token = jsonObject.getString("access_token");
        assertNotNull(access_token);
        final Long expires_in = jsonObject.getLong("expires_in");
        assertTrue(expires_in == 2592000);
    }
}