package io.cjf.jinterviewback.client;

import io.cjf.jinterviewback.pojo.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AMapServiceImplTest {

    @Autowired
    private AMapService aMapService;

    @Test
    void getPosition() {
        String address = "上海市奉贤区五四公路3389号";
        final Position position = aMapService.getPosition(address);
        assertNotNull(position);
        final double longitude = position.getLongitude();
        final double latitude = position.getLatitude();
        assertTrue(-180 < longitude && longitude < 180);
        assertTrue(-85 < latitude && latitude < 85);
    }
}