package io.cjf.jinterviewback.client;

import io.cjf.jinterviewback.pojo.Position;

public interface AMapService {

    Position getPosition(String address);

}
