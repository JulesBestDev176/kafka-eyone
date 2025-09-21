package net.eyone.kafka_eyone.services;

public interface TransformationService {
    Object transform(Object input, String specPath) throws Exception;
}
