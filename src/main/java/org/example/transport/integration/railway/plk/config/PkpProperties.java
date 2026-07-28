package org.example.transport.integration.railway.plk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "provider.pkp")
public record PkpProperties(
        URI baseUrl,
        String apiKey
) { }
