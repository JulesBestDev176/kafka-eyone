package net.eyone.kafka_eyone.services;


import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.models.Patient;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PatientProducerService {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PatientProducerService.class);
    private static final String OUTPUT_BINDING_NAME = "sendPatient-out-0";
    private final StreamBridge streamBridge;

    public void envoyerPatient(Patient patient) {
        LOGGER.info(String.format("Tentative d'envoi du patient via StreamBridge -> %s", patient));

        // On envoie le message sur le "canal" de sortie.
        // Spring Cloud Stream s'occupe de le router vers le bon topic Kafka
        // et de le sérialiser en JSON, comme défini dans la configuration.
        boolean isSent = streamBridge.send(OUTPUT_BINDING_NAME, patient);

        if (isSent) {
            LOGGER.info("Message envoyé avec succès au canal : " + OUTPUT_BINDING_NAME);
        } else {
            LOGGER.info("Échec de l'envoi du message au canal : " + OUTPUT_BINDING_NAME);
        }
    }
}
