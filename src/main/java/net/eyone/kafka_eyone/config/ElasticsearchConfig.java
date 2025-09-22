package net.eyone.kafka_eyone.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private Elasticsearch elasticsearch = new Elasticsearch();

    @Data
    public static class Elasticsearch {
        private String host;
        private int port;
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearch.getHost() + ":" + elasticsearch.getPort())
                .build();
    }
}