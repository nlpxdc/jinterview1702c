package io.cjf.jinterviewsite.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "WechatAPI", url = "https://api.weixin.qq.com")
public interface WechatApi {

    @GetMapping("/sns/oauth2/access_token")
    String getUserAccessToken(@RequestParam String appid,
                              @RequestParam String secret,
                              @RequestParam String code,
                              @RequestParam String grant_type);

    @GetMapping("/sns/userinfo")
    String getUserInfo(@RequestParam String access_token,
                       @RequestParam String openid,
                       @RequestParam String lang);
}
