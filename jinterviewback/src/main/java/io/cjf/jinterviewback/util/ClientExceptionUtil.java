package io.cjf.jinterviewback.util;

import com.alibaba.fastjson.JSON;
import io.cjf.jinterviewback.dto.ClientExceptionDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ClientExceptionUtil {

    public void handle(HttpServletResponse response, Integer status, String errMsg) throws IOException {
        response.setStatus(status);
        final ClientExceptionDTO clientExceptionDTO = new ClientExceptionDTO(status, errMsg);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(clientExceptionDTO));
        response.getWriter().flush();
    }
}
