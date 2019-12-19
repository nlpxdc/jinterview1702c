package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;

public interface BaiduAIService {

    JSONObject ocrIdcard(String photoBase64);


    JSONObject distinguish(String image,String content_type);
}
