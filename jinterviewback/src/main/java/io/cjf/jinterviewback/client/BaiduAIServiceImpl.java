package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import feign.Client;
import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.exception.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.HashMap;

@Service
public class BaiduAIServiceImpl implements BaiduAIService {

    @Autowired
    private BaiduAIApi baiduAIApi;

    @Value("${baidu.apiKey}")
    private String apiKey;

    @Value("${baidu.apiSecret}")
    private String appSecret;

    private String appAccessToken;
    private Long expireTimestamp;

    @Override
    public JSONObject getAppAccessToken() {
        final JSONObject jsonObject = baiduAIApi.getAppAccessToken("client_credentials", apiKey, appSecret);
        return jsonObject;
    }

    @Override
    public JSONObject ocrIdcard(byte[] photo) throws ClientException {
        if (photo.length > 100 * 1024){
            throw new ClientException(ClientExceptionConstant.PHOTO_TOO_LARGE_ERRCODE, ClientExceptionConstant.PHOTO_TOO_LARGE_ERRMSG);
        }

        final String photoBase64 = Base64Utils.encodeToString(photo);

        final HashMap<String, Object> form = new HashMap<>();
        form.put("id_card_side", "front");
        form.put("image", photoBase64);

        autoGetAppAccessToken();
        final JSONObject jsonObject = baiduAIApi.ocrIdcard(appAccessToken, form);

        return jsonObject;
    }

    @Override
    public JSONObject distinguish(String image, String content_type) {

        final HashMap<String, Object> form = new HashMap<>();
        form.put("access_token", appAccessToken);
        form.put("image", image);
        form.put("Content-Type", content_type);

        final JSONObject jsonObject = baiduAIApi.distinguish(form);
        return jsonObject;
    }

    private void autoGetAppAccessToken() {
        final Date now = new Date();
        final long nowTimestamp = now.getTime();
        if (appAccessToken == null || nowTimestamp > expireTimestamp) {
            //todo sync token
            final JSONObject appAccessTokenObj = getAppAccessToken();
            appAccessToken = appAccessTokenObj.getString("access_token");
            final Long expires_in = appAccessTokenObj.getLong("expires_in");
            expireTimestamp = nowTimestamp + expires_in * 1000;
        }
    }
}
