package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AMapApiTest {

    @Autowired
    private AMapApi aMapApi;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void geo() {
        String key = "";
        String address = "上海市奉贤区五四公路3389号";
        final JSONObject geo = aMapApi.geo(address, key);
        assertNotNull(geo);
        final JSONArray geocodes = geo.getJSONArray("geocodes");
        final JSONObject geocode = geocodes.getJSONObject(0);
        final String gpsStr = geocode.getString("location");
        final String[] strings = gpsStr.split(",");
        String longitudeStr = strings[0];
        String latitudeStr = strings[1];
        final double longitude = Double.parseDouble(longitudeStr);
        final double latitude = Double.parseDouble(latitudeStr);
        assertTrue(-180 < longitude && longitude < 180);
        assertTrue(-85 < latitude && latitude < 85);
    }
}