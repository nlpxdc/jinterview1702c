package io.cjf.jinterviewsite.filter;

import io.cjf.jinterviewsite.util.JWTUtil;
import io.cjf.jinterviewsite.vo.StudentLoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Order(1)
@Component
public class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTUtil jwtUtil;

    @Value("${jwt.verify.enable}")
    private Boolean jwtVerifyEnable;

    @Value("${file.extensions}")
    private Set<String> fileExtensions;

    @Value("${jwt.exclude.apiUrls}")
    private Set<String> excludeApiUrls;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        final String requestURI = request.getRequestURI();
        logger.info("request uri: {}", requestURI);

        //skip static resource files
        final String[] splits = requestURI.split("\\.");
        final String extOrigin = splits[splits.length - 1];
        final String ext = extOrigin.toLowerCase();
        if (fileExtensions.contains(ext)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //skip ajax cross origin preflight request
        final String method = request.getMethod();
        if (method.equals("OPTIONS")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (excludeApiUrls.contains(requestURI)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final String token = request.getHeader("jinterviewToken");

        logger.info("verify login with token: {}", token);

        if (jwtVerifyEnable){
            final StudentLoginVO studentLoginVO = jwtUtil.verifyToken(token);
            request.setAttribute("studentId", studentLoginVO.getStudentId());
            request.setAttribute("studentStatus", studentLoginVO.getStatus());
        }else {
            logger.warn("jwt verify disabled!!!");
        }

        filterChain.doFilter(servletRequest, servletResponse);
//        logger.info("login verify finished");
        return;
    }
}
