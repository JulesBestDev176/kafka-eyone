package net.eyone.kafka_eyone.services.organization.interfaces;

import net.eyone.kafka_eyone.dtos.organization.OrganizationElasticDto;

public interface OrganizationProducerService {
    /**
     * Envoie l'organisation sur le topic configuré.
     * @param organization organisation à publier
     */
    void publish(OrganizationElasticDto organization);
}