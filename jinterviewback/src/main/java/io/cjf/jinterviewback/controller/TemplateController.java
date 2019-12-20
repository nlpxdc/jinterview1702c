package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.WechatService;
import io.cjf.jinterviewback.dto.TemplateMessageDTO;
import io.cjf.jinterviewback.service.InterviewService;
import io.cjf.jinterviewback.template.TemplateData;
import io.cjf.jinterviewback.template.TemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/template")
public class TemplateController {

    //引入模板ID
    @Value("${wechat.templateid}")
    private String TemplateId;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private InterviewService interviewService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/test")
    Object  getToken(@RequestParam(defaultValue = "21") Integer interviewId) throws Exception {

        JSONObject getTemToken = wechatService.getAppAccessToken();
        String token = getTemToken.getString("access_token");
        logger.info("token : "+token);

        List<TemplateMessageDTO> templateData = interviewService.getTemById(interviewId);
        String openId = templateData.get(0).getOpenId();
        //创建消息发送实体对象
        TemplateMessage templateMessage=new TemplateMessage();
        templateMessage.setUrl("http://www.baidu.com");//跳转页面
        templateMessage.setTouser(openId);
        logger.info("openId : "+templateMessage.getTouser());
        templateMessage.setTemplate_id(TemplateId);
        //设置模板标题
        Map<String, TemplateData> m = new HashMap<>();
        TemplateData first = new TemplateData();
        first.setValue("面试提醒");
        first.setColor("#FF0000");
        m.put("first",first);
        //设置模板内容
        TemplateData keyword1=new TemplateData();
        keyword1.setValue(templateData.get(0).getCompany());
        keyword1.setColor("#FF0000");
        m.put("keyword1",keyword1);
        //设置模板位置
        TemplateData keyword2=new TemplateData();
        keyword2.setValue(templateData.get(0).getAddress());
        keyword2.setColor("#FF0000");
        m.put("keyword2",keyword2);
        //设置时间
        TemplateData keyword3=new TemplateData();
        SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String format1 = format.format(templateData.get(0).getInterview_time());
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
//        JSONObject getTemplateMessage = wechatService.sendTemplateMessage(token,jsonData);
//        String temMessageStr = getTemplateMessage.toString();
//        logger.info("temMessageStr : "+temMessageStr);
        return "access";
    }

    @Scheduled(fixedRate = 1000*60*5)//隔5分钟执行一次
    public String timerTask(){
        List<TemplateMessageDTO> tempTime = interviewService.getInterviewTime();
        for(int index=0;index<tempTime.size();index++){
            Date time = new Date();//当前时间
            Date interTime = tempTime.get(index).getInterview_time();//面试时间
            if(time.getTime()+10800000 > interTime.getTime()&& interTime.getTime() > time.getTime()+10500000){
                try {
                    logger.info(tempTime.get(index).getInterview_time()+"");
                    getToken(tempTime.get(index).getInterviewId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }
}
