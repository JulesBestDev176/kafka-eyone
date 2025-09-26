package net.eyone.kafka_eyone.services.organization.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.config.AppProperties;
import net.eyone.kafka_eyone.dtos.organization.OrganizationElasticDto;
import net.eyone.kafka_eyone.services.organization.interfaces.OrganizationProducerService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationProducerServiceImpl implements OrganizationProducerService {

    private final StreamBridge streamBridge;
    private final AppProperties appProperties;

    @Override
    public void publish(@Valid OrganizationElasticDto organization) {
        log.info("[OrganizationProducerServiceImpl] [publish] publication de l'organisation dans le topic Kafka: {}", appProperties.getKafka().getOrganizationTopic());
        boolean isSent = streamBridge.send(appProperties.getKafka().getOrganizationTopic(), organization);
        log.info("[OrganizationProducerServiceImpl] [publish] organisation publiée: {}", organization.getClass().getSimpleName());

        if (isSent) {
            log.info("[OrganizationProducerServiceImpl] [publish] message envoyé avec succès au topic: {}", appProperties.getKafka().getOrganizationTopic());
        } else {
            log.error("[OrganizationProducerServiceImpl] [publish] echec de l'envoi du message au topic: {}", appProperties.getKafka().getOrganizationTopic());
        }
    }
}