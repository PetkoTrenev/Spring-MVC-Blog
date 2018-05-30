package com.github.petkotrenev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class VanillaApplication {
    public static void main(String[] args) {
        SpringApplication.run(VanillaApplication.class, args);
    }
}
