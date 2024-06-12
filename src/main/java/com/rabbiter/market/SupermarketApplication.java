package com.rabbiter.market;

import com.rabbiter.market.common.util.PathUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SupermarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupermarketApplication.class,args);
    }

}
