package net.eyone.kafka_eyone.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.PatientTransformationService;
import net.eyone.kafka_eyone.services.PatientWebhookService;
import net.eyone.kafka_eyone.services.ElasticsearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PatientConsumer {

    private final PatientWebhookService webhookService;
    private final PatientTransformationService transformationService;
    private final ElasticsearchService elasticsearchService;
    private final ObjectMapper objectMapper;

    @Bean
    public Consumer<String> consumePatient() {
        return message -> {
            log.info("[PatientConsumer][consumePatient] patient: {}", message);
            Object patientRequest = objectMapper.readValue(message, Object.class);

            // Transformation JOLT
            PatientResponse patientTransforme = transformationService.transformationComplet(patientRequest);
            
            // Envoi webhook
            //webhookService.send(patientTransforme);
            
            // Stockage dans Elasticsearch
            elasticsearchService.indexPatient(patientTransforme);
            
            log.info("[PatientConsumer][consumePatient] patient transformé: {}", patientTransforme);
        };
    }
}
