package io.cjf.jinterviewback.filter;

import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.util.ClientExceptionUtil;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Order(1)
@Component
public class StaticResourceFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${static.resource.extensions}")
    private Set<String> extensions;

    @Autowired
    private ClientExceptionUtil clientExceptionUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String requestURI = request.getRequestURI();

        final String[] strings = requestURI.split("\\.");
        String ext = strings[strings.length - 1];
        ext = ext.toLowerCase();
        if (extensions.contains(ext)){
            clientExceptionUtil.handle(response, HttpStatus.SC_BAD_REQUEST, ClientExceptionConstant.NOT_SUPPORT_STATIC_RESOURCE_ERRMSG);
            return;
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
