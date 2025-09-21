package net.eyone.kafka_eyone.services.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.config.AppProperties;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.services.PatientProducerService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientProducerServiceImpl implements PatientProducerService {

    private final StreamBridge streamBridge;
    private final AppProperties appProperties;

    /**
     * Cette methode permet d'envoyer les infos du patient via StreamBridge
     * @param patient
     */
    @Override
    public void publish(Patient patient) {
        log.info("[PatientProducerServiceImpl] [publish] publication du patient dans le topic Kafka: {}", appProperties.getKafka().getPatientTopic());
        boolean isSent = streamBridge.send(appProperties.getKafka().getOutputBinding(), patient);
        log.info("[PatientProducerServiceImpl] [publish] patient publié: {}", patient);

        if (isSent) {
            log.info("[PatientProducerServiceImpl] [publish] message envoyé avec succès au topic: {}", appProperties.getKafka().getPatientTopic());
        } else {
            log.error("[PatientProducerServiceImpl] [publish] echec de l'envoi du message au topic: {}", appProperties.getKafka().getPatientTopic());
        }
    }
}
