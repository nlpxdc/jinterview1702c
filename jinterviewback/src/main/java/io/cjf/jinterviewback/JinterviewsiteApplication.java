package io.cjf.jinterviewback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("io.cjf.jinterviewback.dao")
@EnableFeignClients
public class JinterviewsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinterviewsiteApplication.class, args);
    }

}
