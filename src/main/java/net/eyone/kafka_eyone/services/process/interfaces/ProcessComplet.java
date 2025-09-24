package net.eyone.kafka_eyone.services.process.interfaces;

public interface ProcessComplet {
    void process(String message, String spec);
}