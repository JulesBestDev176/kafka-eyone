package net.eyone.kafka_eyone.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.PatientWebhookService;
import net.eyone.kafka_eyone.services.TransformationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Consumer;

/**
 * Consommateur Spring Cloud Stream qui reçoit les messages Patient depuis Kafka et les transmet
 * à un webhook via le PatientWebhookService.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class PatientConsumer {

    private final PatientWebhookService webhookService;
    private final TransformationService transformationService;
    private final ObjectMapper objectMapper;

    @Bean
    public Consumer<String> consumePatient() {
        return message -> {
            log.info("[PatientConsumer] Message reçu: {}", message);
            try {
                // Conversion du JSON vers objet
                Object patientRequest = objectMapper.readValue(message, Object.class);
                
                // Transformation JOLT
                PatientResponse patientTransforme = transformationService.transformationComplet(patientRequest, "spec/patient.json");
                
                // Envoi au webhook
                webhookService.sendToWebhook(objectMapper.writeValueAsString(patientTransforme));
                log.info("Webhook envoyé avec succès");
            } catch (Exception e) {
                log.error("Erreur traitement: {}", e.getMessage(), e);
            }
        };
    }
}
