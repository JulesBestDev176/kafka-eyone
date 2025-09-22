package net.eyone.kafka_eyone.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.elasticsearch")
public class ElasticsearchConfig {
    private String host;
    private int port;
    private String index;
}