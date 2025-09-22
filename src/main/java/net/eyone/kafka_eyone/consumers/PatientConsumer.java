package net.eyone.kafka_eyone.consumers;

import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.services.TraitementPatientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class PatientConsumer {

    private final TraitementPatientService traitementPatientService;

    @Bean
    public Consumer<String> consumePatient() {
        return traitementPatientService::traiterPatient;
    }
}
