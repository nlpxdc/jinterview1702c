package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "BaiduAIApi", url = "https://aip.baidubce.com", configuration = BaiduAIApi.Configuration.class)
public interface BaiduAIApi {

    @PostMapping(value = "/rest/2.0/ocr/v1/idcard", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    JSONObject ocrIdcard(@RequestParam String access_token,
                         @RequestBody Map<String, ?> form);

    class Configuration {
        @Bean
        Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }
}
