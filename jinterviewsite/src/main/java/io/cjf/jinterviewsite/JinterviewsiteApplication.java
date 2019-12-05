package io.cjf.jinterviewsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.cjf.jinterviewsite.dao")
public class JinterviewsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinterviewsiteApplication.class, args);
    }

}
