package com.dbybek.ProductService.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // This defines the class as a configuration class. Whenever running this code please create its object.
public class ApplicationConfiguration {

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
