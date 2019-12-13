package io.cjf.jinterviewback.template;

import javax.xml.crypto.Data;

public class TemplateMessage {

    //用户openid
    private String touser;

    //模板消息ID
    private String template_id;

    //详情跳转页面
    private String url;

    //模板数据封装实体
    private Data data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Data getData() {
        return data;
    }

    public void setData(TemplateData data) {
        this.data = (Data) data;
    }
}



