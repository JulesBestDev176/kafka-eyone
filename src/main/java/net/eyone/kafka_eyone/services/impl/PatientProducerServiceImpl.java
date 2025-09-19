package net.eyone.kafka_eyone.services.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.PatientProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientProducerServiceImpl implements PatientProducerService {
    private static final String OUTPUT_BINDING_NAME = "sendPatient-out-0";
    private final StreamBridge streamBridge;

    /**
     * Cette methode permet d'envoyer les infos du patient via StreamBridge
     * @param patientRequest
     */
    @Override
    public void envoyerPatient(Object patientRequest) {
        log.info(String.format("Tentative d'envoi du patient via StreamBridge -> %s", patientRequest));

        // On envoie le message sur le "canal" de sortie.
        // Spring Cloud Stream s'occupe de le router vers le bon topic Kafka
        // et de le sérialiser en JSON, comme défini dans la configuration.
        boolean isSent = streamBridge.send(OUTPUT_BINDING_NAME, patientRequest);

        if (isSent) {
            log.info("Message envoyé avec succès au canal : " + OUTPUT_BINDING_NAME);
        } else {
            log.info("Échec de l'envoi du message au canal : " + OUTPUT_BINDING_NAME);
        }
    }
}
