package org.example.transport.integration.pkp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(PkpProperties.class)
public class PkpClientConfig {

    private static final String X_API_KEY = "X-API-Key";

    @Bean
    RestClient pkpRestClient(RestClient.Builder builder, PkpProperties pkpProperties) {
        return builder.baseUrl(pkpProperties.baseUrl().toString())
                .defaultHeader(X_API_KEY, pkpProperties.apiKey())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
