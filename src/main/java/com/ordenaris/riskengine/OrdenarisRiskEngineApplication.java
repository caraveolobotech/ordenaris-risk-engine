package com.ordenaris.riskengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ordenaris.riskengine")
public class OrdenarisRiskEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdenarisRiskEngineApplication.class, args);
    }
}