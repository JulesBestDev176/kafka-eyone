package net.eyone.kafka_eyone.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.PatientTransformationService;
import net.eyone.kafka_eyone.services.PatientWebhookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PatientConsumer {

    private final PatientWebhookService webhookService;
    private final PatientTransformationService transformationService;

    @Bean
    public Consumer<Patient> consumePatient() {
        return patient -> {
            log.info("[PatientConsumer][consumePatient] réception d'un nouveau patient: {}", patient);
            PatientResponse transformedPatient = transformationService.transform(patient);
            webhookService.send(transformedPatient);
            log.info("[PatientConsumer][consumePatient] patient transformé et envoyé avec succès: {}", transformedPatient);
        };
    }
}
