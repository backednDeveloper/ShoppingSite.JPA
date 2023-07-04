package com.example.jpaproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan("com.example.jpaproject.entity")
public class JavaMailSenderConfig {
    @Bean
    JavaMailSenderImpl javaMailSender(){
        return new JavaMailSenderImpl();
    }
}
