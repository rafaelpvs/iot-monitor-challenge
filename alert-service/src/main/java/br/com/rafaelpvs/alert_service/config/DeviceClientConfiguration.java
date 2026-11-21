package br.com.rafaelpvs.alert_service.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import br.com.rafaelpvs.alert_service.config.properties.DeviceServiceProperties;

@Configuration
@EnableConfigurationProperties(DeviceServiceProperties.class)
public class DeviceClientConfiguration {

  @Bean
  RestClient deviceRestClient(DeviceServiceProperties properties) {
    return RestClient.builder()
        .baseUrl(properties.baseUrl())
        .build();
  }
}
