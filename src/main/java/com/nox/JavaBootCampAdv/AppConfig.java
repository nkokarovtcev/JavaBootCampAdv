package com.nox.JavaBootCampAdv;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@SuppressWarnings("unused")
public class AppConfig {

    @Bean
    public Faker faker() {
        return new Faker();
    }
}