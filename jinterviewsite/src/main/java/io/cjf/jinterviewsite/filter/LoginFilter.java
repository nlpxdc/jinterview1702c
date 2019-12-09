package io.cjf.jinterviewsite.filter;

import io.cjf.jinterviewsite.util.JWTUtil;
import io.cjf.jinterviewsite.vo.StudentLoginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("verify login with token");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        final String token = request.getHeader("jinterviewToken");
        final StudentLoginInfo studentLoginInfo = jwtUtil.verifyToken(token);


        filterChain.doFilter(servletRequest, servletResponse);
    }
}
