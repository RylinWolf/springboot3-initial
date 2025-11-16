package com.wolfhouse.springboot3initial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Rylin Wolf
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
public class Springboot3InitialApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot3InitialApplication.class, args);
    }

}
