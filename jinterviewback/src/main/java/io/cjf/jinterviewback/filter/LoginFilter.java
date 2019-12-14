package io.cjf.jinterviewback.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.enumeration.StudentStatus;
import io.cjf.jinterviewback.exception.ClientException;
import io.cjf.jinterviewback.po.Student;
import io.cjf.jinterviewback.service.StudentService;
import io.cjf.jinterviewback.util.JWTUtil;
import io.cjf.jinterviewback.vo.StudentLoginVO;
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

@Order(2)
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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final String method = request.getMethod();
        if (method.equals("OPTIONS")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final String requestURI = request.getRequestURI();

        if (excludeLoginApiUrls.contains(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = request.getHeader("jinterviewToken");
        if (token == null || token.isEmpty()){
            token = request.getParameter("jinterviewToken");
        }
        if (token == null || token.isEmpty()) {
            throw new ClientException(ClientExceptionConstant.TOKEN_NOT_EXIST_ERRCODE, ClientExceptionConstant.TOKEN_NOT_EXIST_ERRMSG);
        }

        logger.info("verify login with token: {}", token);

        StudentLoginVO studentLoginVO = null;
        try {
            studentLoginVO = jwtUtil.verifyToken(token);
        }catch (JWTVerificationException ex){
            throw new ClientException(ClientExceptionConstant.TOKEN_INVALID_ERRCODE, ex.getMessage());
        }

        request.setAttribute("studentId", studentLoginVO.getStudentId());
        request.setAttribute("openid", studentLoginVO.getOpenid());

        if (excludeSutdentActivateApiUrls.contains(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final Student student = studentService.getByStudentId(studentLoginVO.getStudentId());
        if (student.getStatus() == StudentStatus.NotActivate.ordinal()) {
            throw new ClientException(ClientExceptionConstant.STUDENT_NOT_ACTIVATE_ERRCODE, ClientExceptionConstant.STUDENT_NOT_ACTIVATE_ERRMSG);
        }

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }
}
