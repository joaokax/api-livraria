package com.project.javaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class JavaapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaapiApplication.class, args);
        System.out.println("Documentation :  http://localhost:8080/swagger-ui/index.html#");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder;
//    }
}

// username: João Lucas
// password: 123456789
