package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AMapApi", url = "https://restapi.amap.com")
public interface AMapApi {

    @GetMapping("/v3/geocode/geo")
    JSONObject geo(@RequestParam String address,
                   @RequestParam String key);

}
