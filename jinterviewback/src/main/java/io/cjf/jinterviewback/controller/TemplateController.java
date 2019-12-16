package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.WechatService;
import io.cjf.jinterviewback.dto.InterviewCreateDTO;
import io.cjf.jinterviewback.template.TemplateData;
import io.cjf.jinterviewback.template.TemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/template")
public class TemplateController {

    //引入模板ID
    @Value("${wechat.templateid}")
    private String TemplateId;

    @Autowired
    private WechatService wechatService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/test")
    Object  getToken() throws Exception {

        JSONObject getTemToken = wechatService.getTemToken();
        String token = getTemToken.getString("access_token");
        logger.info("token : "+token);

        //创建消息发送实体对象
        TemplateMessage templateMessage=new TemplateMessage();
        templateMessage.setUrl("http://www.baidu.com");//跳转页面
        templateMessage.setTouser("oHiGkxCX4TsoPkPs8qxQPWWNzAOQ");
        templateMessage.setTemplate_id(TemplateId);
        //设置模板标题
        Map<String, TemplateData> m = new HashMap<>();
        TemplateData first = new TemplateData();
        first.setValue("面试提醒");
        first.setColor("#FF0000");
        m.put("first",first);
        //设置模板内容
        TemplateData keyword1=new TemplateData();
        keyword1.setValue("八维食堂");
        keyword1.setColor("#FF0000");
        m.put("keyword1",keyword1);
        //设置模板位置
        TemplateData keyword2=new TemplateData();
        keyword2.setValue("上海市奉贤区");
        keyword2.setColor("#FF0000");
        m.put("keyword2",keyword2);
        //设置时间
        TemplateData keyword3=new TemplateData();
        SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String format1 = format.format(new Date());
        keyword3.setValue(format1);
        keyword3.setColor("#FF0000");
        m.put("keyword3",keyword3);
        //设置跳转内容
        TemplateData remark=new TemplateData();
        remark.setValue("点此处查看详情");
        remark.setColor("#FF0000");
        m.put("remark",remark);
        templateMessage.setData(m);

        JSONObject jsonData = (JSONObject) JSONObject.toJSON(templateMessage);
        JSONObject getTemplateMessage = wechatService.templateMessage(token,jsonData);
        String temMessageStr = getTemplateMessage.toString();
        logger.info("temMessageStr : "+temMessageStr);
        return "";
    }
}