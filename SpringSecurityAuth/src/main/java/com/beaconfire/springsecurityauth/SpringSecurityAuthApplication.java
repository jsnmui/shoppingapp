package com.beaconfire.springsecurityauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringSecurityAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAuthApplication.class, args);
    }

}
