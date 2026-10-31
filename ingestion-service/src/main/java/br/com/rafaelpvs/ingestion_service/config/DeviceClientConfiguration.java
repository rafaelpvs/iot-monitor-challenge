package br.com.rafaelpvs.ingestion_service.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import br.com.rafaelpvs.ingestion_service.config.properties.DeviceServiceProperties;

@Configuration
@EnableConfigurationProperties(DeviceServiceProperties.class)
public class DeviceClientConfiguration {

  @Bean
  RestClient restClient(
      RestClient.Builder builder,
      DeviceServiceProperties properties) {
    return builder.baseUrl(properties.baseUrl()).build();
  }
}
