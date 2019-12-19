package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BaiduAIServiceImpl implements BaiduAIService {

    @Autowired
    private BaiduAIApi baiduAIApi;

    @Value("${testcase.baidu.ai.access_token}")
    private String accessToken;

    @Override
    public JSONObject ocrIdcard(String photoBase64) {

        final HashMap<String, Object> form = new HashMap<>();
        form.put("id_card_side", "front");
        form.put("image", photoBase64);

        final JSONObject jsonObject = baiduAIApi.ocrIdcard(accessToken, form);

        return jsonObject;
    }

    @Override
    public JSONObject distinguish(String image, String content_type) {

        final HashMap<String, Object> form = new HashMap<>();
        form.put("access_token", accessToken);
        form.put("image", image);
        form.put("Content-Type", content_type);

        final JSONObject jsonObject = baiduAIApi.distinguish(form);
        return jsonObject;
    }
}
