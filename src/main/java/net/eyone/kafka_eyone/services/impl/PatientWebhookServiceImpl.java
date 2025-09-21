package net.eyone.kafka_eyone.services.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.config.AppProperties;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.PatientWebhookService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientWebhookServiceImpl implements PatientWebhookService {

    private final WebClient webClient;
    private final AppProperties appProperties;
    @Override
    public void send(PatientResponse patient) {
        webClient.post()
                .uri(appProperties.getWebhook().getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patient)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofMillis(appProperties.getWebhook().getTimeout()))
                .retryWhen(
                    Retry.backoff(appProperties.getWebhook().getRetryAttempts(), Duration.ofSeconds(1))
                        .maxBackoff(Duration.ofSeconds(5))
                        .doBeforeRetry(signal -> 
                            log.warn("[PatientWebhookServiceImpl][send] tentative de renvoi {} après erreur",
                                signal.totalRetries() + 1))
                )
                .doOnSuccess(response -> 
                    log.info("[PatientWebhookServiceImpl][send] patient envoyé avec succès au webhook"))
                .doOnError(error -> 
                    log.error("[PatientWebhookServiceImpl][send] echec de l'envoi au webhook après {} tentatives: {}",
                        appProperties.getWebhook().getRetryAttempts(), 
                        error.getMessage()))
                .subscribe();
    }
}
