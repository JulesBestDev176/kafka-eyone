package net.eyone.kafka_eyone.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elasticsearch configuration class for Transaction API
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "net.eyone.kafka_eyone.repositories.elastic")
public class ElasticsearchConfig extends ReactiveElasticsearchConfiguration {
    @Value("${spring.elasticsearch.uris:localhost:9200}")
    private String uri;
//    @Value("${spring.elasticsearch.username:}")
//    private String username;
//    @Value("${spring.elasticsearch.password:}")
//    private String password;
//    @Value("${elasticsearch.index.transactions.name}")
//    private String transactionIndex;
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(uri)
//                .withBasicAuth(username, password)
                .build();
    }
}
