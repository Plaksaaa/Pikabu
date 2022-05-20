package com.plaxa.pikabu;

import com.plaxa.pikabu.configuration.property.FirebaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FirebaseProperties.class)
public class PikabuApplication {

    public static void main(String[] args) {
        SpringApplication.run(PikabuApplication.class, args);
    }

}
