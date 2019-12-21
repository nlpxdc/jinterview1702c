package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.exception.ClientException;

public interface BaiduAIService {

    JSONObject getAppAccessToken();

    JSONObject ocrIdcard(byte[] photo) throws ClientException;

    JSONObject ocrGeneralBasic(byte[] photo) throws ClientException;

}
