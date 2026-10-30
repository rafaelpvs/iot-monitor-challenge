package br.com.rafaelpvs.ingestion_service.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics.telemetry-received")
public record TelemetryReceivedTopicProperties(
    String name,
    int partitions,
    int replicationFactor) {
}
