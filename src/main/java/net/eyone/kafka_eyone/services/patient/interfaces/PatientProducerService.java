package net.eyone.kafka_eyone.services.patient.interfaces;

import net.eyone.kafka_eyone.dtos.patient.PatientElasticDto;

public interface PatientProducerService {
    /**
     * Envoie le patient sur le topic configuré.
     * @param patient patient à publier
     */
    void publish(PatientElasticDto patient);
}