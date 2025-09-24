package net.eyone.kafka_eyone.services.core.interfaces;

public interface MongoService {
    <T> void save(T entity);
}