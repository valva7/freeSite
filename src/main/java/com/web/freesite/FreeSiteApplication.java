package com.web.freesite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Spring Security 기능 off
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FreeSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeSiteApplication.class, args);
    }

}
