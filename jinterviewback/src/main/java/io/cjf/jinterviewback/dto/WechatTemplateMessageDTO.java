package io.cjf.jinterviewback.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WechatTemplateMessageDTO{

	@JsonProperty("touser")
	private String touser;

	@JsonProperty("data")
	private JSONObject data;

	@JsonProperty("template_id")
	private String templateId;

	@JsonProperty("url")
	private String url;

	public void setTouser(String touser){
		this.touser = touser;
	}

	public String getTouser(){
		return touser;
	}

	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}

	public String getTemplateId(){
		return templateId;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
}