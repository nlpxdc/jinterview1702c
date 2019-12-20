package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.dto.WechatTemplateMessageDTO;

public interface WechatService {

    JSONObject getUserAccessToken(String code);

    JSONObject getUserInfo(String accessToken);

    JSONObject getAppAccessToken();

    Long sendTemplateMessage(WechatTemplateMessageDTO templateMessageDTO);

}
