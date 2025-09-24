package net.eyone.kafka_eyone.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.services.process.interfaces.ProcessComplet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PatientConsumer {

    static {
        System.setProperty("spring.cloud.function.scan.packages", "net.eyone.kafka_eyone.consumers");
    }

    private final ProcessComplet processComplet;

    @Bean
    public Consumer<String> consumePatient() {
        return message -> {
            log.info("[PatientConsumer] [consumePatient] Message reçu: {}", message);
            processComplet.process(message, "spec/patient.json");
            log.info("[PatientConsumer] [consumePatient] Traitement terminé");
        };
    }
}
