package io.cjf.jinterviewback.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(2)
@Component
public class PreflightFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String method = request.getMethod();

        if (method.equals("OPTIONS")){
            return;
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
