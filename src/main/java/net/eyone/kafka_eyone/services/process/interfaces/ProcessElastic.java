package net.eyone.kafka_eyone.services.process.interfaces;

import net.eyone.kafka_eyone.dtos.ProcessResponse;

import java.util.UUID;

public interface ProcessElastic {
    <T> ProcessResponse<T> saveToElastic(T data);
    <T> ProcessResponse<T> updateElastic(UUID id, String statut);
}