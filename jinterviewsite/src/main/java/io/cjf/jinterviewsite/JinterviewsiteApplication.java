package io.cjf.jinterviewsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("io.cjf.jinterviewsite.dao")
@EnableFeignClients
public class JinterviewsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinterviewsiteApplication.class, args);
    }

}
