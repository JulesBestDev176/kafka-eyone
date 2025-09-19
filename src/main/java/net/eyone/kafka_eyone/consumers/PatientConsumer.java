package net.eyone.kafka_eyone.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.PatientWebhookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public Consumer<String> consumePatient() {
        return message -> {
            log.info("[PatientConsumer] [consumePatient] Message : {}", message);
        };
    }
}
