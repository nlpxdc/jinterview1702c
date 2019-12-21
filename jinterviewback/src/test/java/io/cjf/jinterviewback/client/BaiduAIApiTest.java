package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

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

    @Test
    void ocrIdcard() throws IOException {
        String access_token = "";
        String pathname = "img/IdCardFront360p.jpg";
        final File file = new File(pathname);
        final byte[] bytes = Files.readAllBytes(file.toPath());
        final String photoBase64 = Base64Utils.encodeToString(bytes);

        final HashMap<String, Object> form = new HashMap<>();
        form.put("id_card_side", "front");
        form.put("image", photoBase64);

        final JSONObject jsonObject = baiduAIApi.ocrIdcard(access_token, form);
        assertNotNull(jsonObject);
        final JSONObject words_result = jsonObject.getJSONObject("words_result");
        final JSONObject nameObj = words_result.getJSONObject("姓名");
        final String name = nameObj.getString("words");
        assertNotNull(name);
    }
}