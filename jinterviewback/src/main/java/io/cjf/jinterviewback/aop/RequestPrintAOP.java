package io.cjf.jinterviewback.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestPrintAOP {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(public * io.cjf.jinterviewback.controller.*.*(..))")
    public void beforeApi(JoinPoint joinPoint){
        logger.info("api args: {}", joinPoint.getArgs());
    }

    @Before("@annotation(io.cjf.jinterviewback.annotation.NotRequiredLogin)")
    public void checkNotRequiredLogin(){
        logger.info("not required login");
    }


}
