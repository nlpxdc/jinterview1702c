package io.cjf.jinterviewback.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.pojo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AMapServiceImpl implements AMapService {

    @Autowired
    private AMapApi aMapApi;

    @Value("${amap.webapi.key}")
    private String key;

    @Override
    public Position getPosition(String address) {
        final JSONObject geo = aMapApi.geo(address, key);
        final JSONArray geocodes = geo.getJSONArray("geocodes");
        final JSONObject geocode = geocodes.getJSONObject(0);
        final String gpsStr = geocode.getString("location");
        final String[] strings = gpsStr.split(",");
        String longitudeStr = strings[0];
        String latitudeStr = strings[1];
        final double longitude = Double.parseDouble(longitudeStr);
        final double latitude = Double.parseDouble(latitudeStr);
        final Position position = new Position();
        position.setLongitude(longitude);
        position.setLatitude(latitude);
        return position;
    }
}
