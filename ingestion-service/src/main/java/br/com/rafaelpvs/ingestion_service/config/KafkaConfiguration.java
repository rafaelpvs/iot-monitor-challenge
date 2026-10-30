package br.com.rafaelpvs.ingestion_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import br.com.rafaelpvs.ingestion_service.config.properties.TelemetryReceivedTopicProperties;

@Configuration
@EnableConfigurationProperties(TelemetryReceivedTopicProperties.class)
public class KafkaConfiguration {

  @Bean
  NewTopic telemetryReceivedTopic(TelemetryReceivedTopicProperties properties) {
    return TopicBuilder.name(properties.name())
        .partitions(properties.partitions())
        .replicas(properties.replicationFactor())
        .build();
  }
}
