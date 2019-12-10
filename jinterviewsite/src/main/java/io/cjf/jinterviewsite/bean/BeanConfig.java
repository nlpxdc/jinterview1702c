package io.cjf.jinterviewsite.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class BeanConfig {

    @Bean
    public SecureRandom getSecureRandom(){
        return new SecureRandom();
    }
}
