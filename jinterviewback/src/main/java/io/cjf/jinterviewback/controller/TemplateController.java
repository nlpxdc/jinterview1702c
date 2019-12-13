package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.dto.InterviewCreateDTO;
import io.cjf.jinterviewback.template.TemplateData;
import io.cjf.jinterviewback.template.TemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    private InterviewCreateDTO interviewCreateDTO;

    private final String appId = "wx0f80605c53171199";

    private final String secret = "ba5c9d4cb324450ea9c715d68814c548";

    @GetMapping("/test")
    Object  getToken() throws Exception {

        String token = "28_DcKL54UUHME2tsqQe-bizgP-xyZc8uummGFhwCSjd8fDBxuzIA36V_4D7X5Bs6RObb8mUW0eXX-jsk3h72VRDTMF7nGmGQnJ2rc0YCQ789FKkZgJqcXedv-zRL2IJRkRqjI5s5vTbCi3BdejCOXeABASEN";

        //获得access_token的接口
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + appId + "&secret=" + secret;
        System.out.println("getTokenUrl="+getTokenUrl);
        String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
        System.out.println("sendUrl="+sendUrl);
        //创建消息发送实体对象
        TemplateMessage templateMessage=new TemplateMessage();
        templateMessage.setUrl("http://www.baidu.com");//跳转页面
        templateMessage.setTouser("oHiGkxGvu4QxawlIhtJuY4Mwjvrw");
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

        //将封装的数据转成JSON
        String jsonString = JSON.toJSONString(templateMessage);
        System.out.println(jsonString);
        PrintWriter out = null;
        BufferedReader in = null;
        JSONObject jsonObject = null;
        String result = "";
        URL realUrl = new URL(sendUrl);

        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流（设置请求编码为UTF-8）
        out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        // 发送请求参数
        out.print(jsonString);
        // flush输出流的缓冲
        out.flush();
        // 获取请求返回数据（设置返回数据编码为UTF-8）
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }

        System.out.println(result);
        return "";
    }
}
