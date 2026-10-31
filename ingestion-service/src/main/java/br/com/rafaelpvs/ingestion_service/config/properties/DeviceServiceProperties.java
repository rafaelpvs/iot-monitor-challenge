package br.com.rafaelpvs.ingestion_service.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.clients.device-service")
public record DeviceServiceProperties(String baseUrl) {
}
