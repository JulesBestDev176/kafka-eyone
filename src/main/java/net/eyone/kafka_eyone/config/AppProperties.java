package net.eyone.kafka_eyone.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Kafka kafka = new Kafka();
    private Webhook webhook = new Webhook();

    @Data
    public static class Kafka {
        private String patientTopic;
        private String outputBinding;
    }

    @Data
    public static class Webhook {
        private String url;
        private int timeout;
        private int retryAttempts;
    }
}
