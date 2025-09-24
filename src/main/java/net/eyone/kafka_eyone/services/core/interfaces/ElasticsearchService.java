package net.eyone.kafka_eyone.services.core.interfaces;

import java.util.UUID;

public interface ElasticsearchService {
    <T> T save(T entity);
    <T> T updateStatus(UUID id, String status);
}