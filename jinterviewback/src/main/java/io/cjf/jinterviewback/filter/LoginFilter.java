package io.cjf.jinterviewback.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.enumeration.StudentStatus;
import io.cjf.jinterviewback.po.Student;
import io.cjf.jinterviewback.service.StudentService;
import io.cjf.jinterviewback.util.ClientExceptionUtil;
import io.cjf.jinterviewback.util.JWTUtil;
import io.cjf.jinterviewback.vo.StudentLoginVO;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Order(3)
@Component
public class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTUtil jwtUtil;

    @Value("${jwt.exclude.apiUrls}")
    private Set<String> excludeLoginApiUrls;

    @Value("${student-activate.exclude.apiUrls}")
    private Set<String> excludeSutdentActivateApiUrls;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClientExceptionUtil clientExceptionUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String requestURI = request.getRequestURI();
        final String method = request.getMethod();
        logger.info("request uri: {} {}", method, requestURI);

        if (excludeLoginApiUrls.contains(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final String token = request.getHeader("jinterviewToken");
        if (token == null || token.isEmpty()) {
            clientExceptionUtil.handle(response, HttpStatus.SC_UNAUTHORIZED, ClientExceptionConstant.TOKEN_NOT_EXIST_ERRMSG);
            return;
        }

        logger.info("verify login with token: {}", token);

        StudentLoginVO studentLoginVO = null;
        try {
            studentLoginVO = jwtUtil.verifyToken(token);
        }catch (JWTVerificationException ex){
            clientExceptionUtil.handle(response, HttpStatus.SC_UNAUTHORIZED, ex.getMessage());
            return;
        }

        request.setAttribute("studentId", studentLoginVO.getStudentId());
        request.setAttribute("openid", studentLoginVO.getOpenid());

        if (excludeSutdentActivateApiUrls.contains(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final Student student = studentService.getBystudentId(studentLoginVO.getStudentId());
        if (student.getStatus() == StudentStatus.NotActivate.ordinal()) {
            clientExceptionUtil.handle(response, HttpStatus.SC_FORBIDDEN, ClientExceptionConstant.TOKEN_INVALID_ERRMSG);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
//        logger.info("login verify finished");
        return;
    }
}
