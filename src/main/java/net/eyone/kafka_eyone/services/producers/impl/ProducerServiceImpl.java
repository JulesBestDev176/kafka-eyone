package net.eyone.kafka_eyone.services.producers.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.config.AppProperties;

import net.eyone.kafka_eyone.services.ProducerService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerServiceImpl implements ProducerService {

    private final StreamBridge streamBridge;
    private final AppProperties appProperties;

    /**
     * Cette methode permet d'envoyer les infos de l'entité via StreamBridge
     * @param entity
     */
    @Override
    public <T> void publish(T entity) {
        log.info("[PatientProducerServiceImpl] [publish] publication de l'entité dans le topic Kafka: {}", appProperties.getKafka().getPatientTopic());
        boolean isSent = streamBridge.send(appProperties.getKafka().getPatientTopic(), entity);
        log.info("[PatientProducerServiceImpl] [publish] entité publiée: {}", entity.getClass().getSimpleName());

        if (isSent) {
            log.info("[PatientProducerServiceImpl] [publish] message envoyé avec succès au topic: {}", appProperties.getKafka().getPatientTopic());
        } else {
            log.error("[PatientProducerServiceImpl] [publish] echec de l'envoi du message au topic: {}", appProperties.getKafka().getPatientTopic());
        }
    }
}
