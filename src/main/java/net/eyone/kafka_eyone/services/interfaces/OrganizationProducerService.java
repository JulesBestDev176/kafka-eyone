package net.eyone.kafka_eyone.services.interfaces;

import jakarta.validation.Valid;
import net.eyone.kafka_eyone.dtos.OrganizationElasticDto;

public interface OrganizationProducerService {
    /**
     * Envoie l'organisation sur le topic configuré.
     * @param organization organisation à publier
     */
    void publish(OrganizationElasticDto organization);
}