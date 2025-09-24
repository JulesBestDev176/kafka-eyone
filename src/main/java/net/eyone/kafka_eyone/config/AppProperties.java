package net.eyone.kafka_eyone.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class AppProperties {
    private Kafka kafka = new Kafka();

    @Data
    public static class Kafka {
        private String patientTopic;
    }
}
