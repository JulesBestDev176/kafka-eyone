package net.eyone.kafka_eyone.services.process.interfaces;

import net.eyone.kafka_eyone.dtos.ProcessResponse;

import java.util.UUID;

public interface ProcessMongo {
    <T> ProcessResponse<T> saveToMongo(Object data, String spec, Class<T> targetClass, UUID elasticId);
    ProcessResponse<?> saveToMongo(Object data, String spec, UUID elasticId);
}