package net.eyone.kafka_eyone.services.interfaces;

import jakarta.validation.Valid;
import net.eyone.kafka_eyone.dtos.PatientElasticDto;

public interface PatientProducerService {
    /**
     * Envoie le patient sur le topic configuré.
     * @param patient patient à publier
     */
    void publish(PatientElasticDto patient);
}