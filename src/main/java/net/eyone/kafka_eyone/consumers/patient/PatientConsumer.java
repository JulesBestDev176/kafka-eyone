package net.eyone.kafka_eyone.consumers.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.models.patient.PatientElastic;
import net.eyone.kafka_eyone.services.ProcessPatientService;
import net.eyone.kafka_eyone.utils.Base64Util;
import net.eyone.kafka_eyone.utils.ObjectMapperUtil;
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
            try {
                log.info("[PatientConsumer] [consumePatient] message : {}", message);
                
                Object decodedData = Base64Util.decodeBase64Data(message);
                log.debug("[PatientConsumer] [consumePatient] decodedData : {}", decodedData);

                PatientElastic patient = ObjectMapperUtil.jsonToObject(decodedData, PatientElastic.class);
                log.debug("[PatientConsumer] [consumePatient] patient : {}", patient);
                
                processPatient.processSavePatient(patient);
                log.info("[PatientConsumer] [consumePatient] result : success");
            } catch (Exception e) {
                log.error("[PatientConsumer] [consumePatient] error : {}", e.getMessage());
                throw e;
            }
        };
    }

}
