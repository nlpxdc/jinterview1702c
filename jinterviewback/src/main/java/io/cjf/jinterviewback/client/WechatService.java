package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.dto.WechatTemplateMessageDTO;

public interface WechatService {

    JSONObject getUserAccessToken(String code);

    JSONObject getAppAccessToken();

    JSONObject sendTemplateMessage(WechatTemplateMessageDTO templateMessageDTO);

    JSONObject getUserInfo(String accessToken);

}
