package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSON;
import io.cjf.jinterviewback.dto.InterviewCreateDTO;
import io.cjf.jinterviewback.template.TemplateContent;
import io.cjf.jinterviewback.template.TemplateData;
import io.cjf.jinterviewback.template.TemplateMessage;
import io.cjf.jinterviewback.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

@RequestMapping("/template")
public class TemplateController {

    //引入模板ID
    @Value("${templateid}")
    private String TemplateId;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private InterviewCreateDTO interviewCreateDTO;

    @GetMapping("/test")
    public String test(){
        //创建消息发送实体对象
        TemplateMessage templateMessage=new TemplateMessage();
        templateMessage.setUrl("www.baidu.com");//跳转页面
        templateMessage.setTouser("oKWFV1K4-88PuhQiw80syHB");
        templateMessage.setTemplate_id(TemplateId);
        //设置模板标题
        TemplateContent first=new TemplateContent();
        first.setValue("面试提醒");
        first.setColor("#FF0000");
        //设置模板内容
        TemplateContent keyword1=new TemplateContent();
        keyword1.setValue(interviewCreateDTO.getCompany());
        keyword1.setColor("#FF0000");
        //设置模板位置
        TemplateContent keyword2=new TemplateContent();
        keyword2.setValue(interviewCreateDTO.getAddress());
        keyword2.setColor("#FF0000");
        //设置时间
        TemplateContent keyword3=new TemplateContent();
        SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String format1 = format.format(interviewCreateDTO.getTime());
        keyword3.setValue(format1);
        keyword3.setColor("#FF0000");
        //设置跳转内容
        TemplateContent remark=new TemplateContent();
        remark.setValue("点此处查看详情");
        remark.setColor("#FF0000");
        //创建模板信息数据对象
        TemplateData data = new TemplateData();
        data.setFirst(first);
        data.setKeyword1(keyword1);
        data.setKeyword2(keyword2);
        data.setKeyword3(keyword3);
        data.setRemark(remark);
        templateMessage.setData(data);

        //将封装的数据转成JSON
        String jsonString = JSON.toJSONString(templateMessage);
        templateService.TemplateMessage(jsonString);
        return jsonString;
    }
}
