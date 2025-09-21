package net.eyone.kafka_eyone.services;

import net.eyone.kafka_eyone.dtos.PatientResponse;

public interface PatientWebhookService {
    void send(PatientResponse patient);
}
