package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "WechatAPI", url = "https://api.weixin.qq.com")
public interface WechatApi {

    @GetMapping("/sns/oauth2/access_token")
    String getUserAccessToken(@RequestParam String appid,
                              @RequestParam String secret,
                              @RequestParam String code,
                              @RequestParam String grant_type);

    @GetMapping("/cgi-bin/token")
    String getTemToken(@RequestParam String appid,
                       @RequestParam String secret,
                       @RequestParam String grant_type);

    @PostMapping("/cgi-bin/message/template/send")
    String templateMessage(@RequestParam String access_token,
                           @RequestBody JSON jsonData);

    @GetMapping("/sns/userinfo")
    String getUserInfo(@RequestParam String access_token,
                       @RequestParam String openid,
                       @RequestParam String lang);
}
