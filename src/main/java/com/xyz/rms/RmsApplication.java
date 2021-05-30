package com.xyz.rms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * Main Class which runs the Applications.
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
@SpringBootApplication
@EnableCircuitBreaker
public class RmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmsApplication.class, args);
    }

}
