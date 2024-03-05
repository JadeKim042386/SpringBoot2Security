package joo.example.springboot2security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringBoot2SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2SecurityApplication.class, args);
    }
}
