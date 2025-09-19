package net.eyone.kafka_eyone.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.services.PatientWebhookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implémentation par défaut qui publie la charge utile Patient vers une URL de webhook via WebClient.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PatientWebhookServiceImpl implements PatientWebhookService {

    private final WebClient webClient = WebClient.builder().build();

    @Value("${webhook.patient.url:https://webhook.site/17622a41-f27e-43cc-b34a-aea241c456d1}")
    private String patientWebhookUrl;

    @Override
    public void sendToWebhook(String message) {

        log.info("Envoi du patient vers le webhook {}", patientWebhookUrl);
        webClient.post()
                .uri(patientWebhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(res -> log.info("Webhook accepté: {}", res.getStatusCode()))
                .doOnError(err -> log.error("Erreur lors de l'appel au webhook", err))
                .subscribe();
    }
}
