package net.eyone.kafka_eyone.consumers;

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

    @Bean
    public Consumer<String> consumePatient() {
        return message -> {
            log.info("[PatientConsumer][consumePatient] patient: {}", message);
            PatientResponse transformedPatient = transformationService.transformationComplet(message);
            
            // Envoi webhook
            //webhookService.send(transformedPatient);
            
            // Stockage dans Elasticsearch
            elasticsearchService.indexPatient(transformedPatient);
            
            log.info("[PatientConsumer][consumePatient] patient transformé: {}", transformedPatient);
        };
    }
}
