package net.eyone.kafka_eyone.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.models.PatientElastic;
import net.eyone.kafka_eyone.services.ProcessPatientService;
import net.eyone.kafka_eyone.utils.TransformationUtil;
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

    private final ProcessPatientService processPatient;

    @Bean
    public Consumer<String> consumePatient() {
        return message -> {
            log.info("[PatientConsumer] [consumePatient] Message reçu: {}", message);
            Object decodedData = TransformationUtil.Base64Decoder.decodeBase64Data(message);

            PatientElastic patient = TransformationUtil.ObjectMapperTransformation.jsonToObject(decodedData, PatientElastic.class);
            
            processPatient.processSavePatient(patient);
            log.info("[PatientConsumer] [consumePatient] Traitement terminé");
        };
    }

}
