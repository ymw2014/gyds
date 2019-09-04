package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
public class ManagerMain {

    public static void main(String[] args) {
    	
    	
        SpringApplication.run(ManagerMain.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    welcome      ヾ(◍°∇°◍)ﾉﾞ\n");
    }

}

