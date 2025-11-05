package com.wolfhouse.springboot3initial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author Rylin Wolf
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class Springboot3InitialApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot3InitialApplication.class, args);
    }

}
