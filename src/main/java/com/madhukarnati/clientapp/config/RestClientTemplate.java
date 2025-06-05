package com.madhukarnati.clientapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.madhukarnati.clientapp.BmoClientApplication;

@Configuration
public class RestClientTemplate {
	
	private static final Logger log = LoggerFactory.getLogger(RestClientTemplate.class);
	
	@Value("${serviceprovider.baseUrl}")
	private String baseUrl;
	
	@Bean
    public WebClient webClient(WebClient.Builder builder) {
		log.info("BasePath is..{}",baseUrl);
        return builder.baseUrl(baseUrl)
                      .build();
    }
}
