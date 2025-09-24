package net.eyone.kafka_eyone.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.models.OrganizationElastic;
import net.eyone.kafka_eyone.services.ProcessOrganizationService;
import net.eyone.kafka_eyone.services.ProcessPatientService;
import net.eyone.kafka_eyone.utils.TransformationUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OrganizationConsumer {

    static {
        System.setProperty("spring.cloud.function.scan.packages", "net.eyone.kafka_eyone.consumers");
    }

    private final ProcessOrganizationService processOrganization;



    @Bean
    public Consumer<String> consumeOrganization() {
        return message -> {
            log.info("[OrganizationConsumer] [consumeOrganization] Message reçu: {}", message);
            Object decodedData = TransformationUtil.Base64Decoder.decodeBase64Data(message);

            OrganizationElastic organization = TransformationUtil.ObjectMapperTransformation.jsonToObject(decodedData, OrganizationElastic.class);

            processOrganization.processSaveOrganization(organization);
            log.info("[OrganizationConsumer] [consumeOrganization] Traitement terminé");
        };
    }
}
