package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.exception.ClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaiduAIServiceImplTest {

    @Autowired
    private BaiduAIService baiduAIService;

    @Test
    void getAppAccessToken(){
        final JSONObject appAccessToken = baiduAIService.getAppAccessToken();
        final String access_token = appAccessToken.getString("access_token");
        assertNotNull(access_token);
    }

    @Test
    void ocrIdcard() throws IOException, ClientException {
        String pathname = "img/IdCardFront360p.jpg";
        final File file = new File(pathname);
        final byte[] bytes = Files.readAllBytes(file.toPath());
        final JSONObject jsonObject = baiduAIService.ocrIdcard(bytes);
        assertNotNull(jsonObject);
        final JSONObject words_result = jsonObject.getJSONObject("words_result");
        final JSONObject nameObj = words_result.getJSONObject("姓名");
        final String name = nameObj.getString("words");
        assertNotNull(name);
    }

    @Test
    void ocrGeneralBasic() throws IOException, ClientException {
        String pathname = "img/exam720p.jpg";
        final File file = new File(pathname);
        final byte[] bytes = Files.readAllBytes(file.toPath());
        final JSONObject jsonObject = baiduAIService.ocrGeneralBasic(bytes);
        assertNotNull(jsonObject);
        final JSONArray words_result = jsonObject.getJSONArray("words_result");
        final int size = words_result.size();
        assertTrue(size != 0);
    }
}