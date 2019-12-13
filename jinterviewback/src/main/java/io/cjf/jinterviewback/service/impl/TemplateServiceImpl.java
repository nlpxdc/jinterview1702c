package io.cjf.jinterviewback.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.service.TemplateService;
import io.cjf.jinterviewback.template.TemplateMessage;
import org.springframework.stereotype.Service;


@Service
public class TemplateServiceImpl implements TemplateService {

    /**
     * 模板消息请求路径
     */
    private static String  TemplateMessage_Url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     *获取token路径
     */
    private static String AccessTokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";

    private static String appId = "wx0c14a6dfeab19166";

    private static String secret = "02a53bc213a98bb9fa6aae4157fd55eb";

    @Override
    public String TemplateMessage(String jsonString) {
        //获取token
        String token = AccessTokenUrl.replace("APPID",appId).replace("SECRET",secret);

        TemplateMessage templateMessage = new TemplateMessage();

        String url=TemplateMessage_Url.replace("ACCESS_TOKEN",token);

        //调用接口发送
        JSONObject jsonObject = httpRequest(url,"POST",jsonString);
        Integer errcode = jsonObject.getInteger("errcode");
        return null;
    }

    private JSONObject httpRequest(String url, String post, String jsonString) {

        return null;
    }
}
