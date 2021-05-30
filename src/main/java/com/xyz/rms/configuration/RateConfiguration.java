package com.xyz.rms.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for creating Rest Template Bean
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
@Configuration
public class RateConfiguration {

    /**
     * Creating TestTemplate Object with 5 Second as Timeout Period
     *
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}