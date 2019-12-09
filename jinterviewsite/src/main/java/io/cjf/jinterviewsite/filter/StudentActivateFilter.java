package io.cjf.jinterviewsite.filter;

import io.cjf.jinterviewsite.constant.ClientExceptionConstant;
import io.cjf.jinterviewsite.enumeration.StudentStatus;
import io.cjf.jinterviewsite.exception.ClientRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Order(2)
@Component
public class StudentActivateFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${student-activate.enable}")
    private Boolean enabled;

    @Value("${student-activate.exclude.apiUrls}")
    private List<String> excludeApiUrls;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        final String requestURI = request.getRequestURI();

        if (excludeApiUrls.contains(requestURI)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (enabled){
            final Object studentStatus = (byte)request.getAttribute("studentStatus");
            if (studentStatus == StudentStatus.NotActivate){
                throw new ClientRuntimeException(ClientExceptionConstant.STUDENT_NOT_ACTIVATE_ERRCODE, ClientExceptionConstant.STUDENT_NOT_ACTIVATE_ERRMSG);
            }
        }else {
            logger.warn("student activate disabled");
        }

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }
}
