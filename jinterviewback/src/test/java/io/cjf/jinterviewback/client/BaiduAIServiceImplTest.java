package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
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
    void ocrIdcard() throws IOException {
        String pathname = "img/IdCardFront360p.jpg";
        final File file = new File(pathname);
        final byte[] bytes = Files.readAllBytes(file.toPath());
        final String photoBase64 = Base64Utils.encodeToString(bytes);
        final JSONObject jsonObject = baiduAIService.ocrIdcard(photoBase64);
        assertNotNull(jsonObject);
    }
}