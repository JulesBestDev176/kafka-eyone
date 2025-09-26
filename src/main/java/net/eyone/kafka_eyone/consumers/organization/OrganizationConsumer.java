package net.eyone.kafka_eyone.consumers.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.models.organization.OrganizationElastic;
import net.eyone.kafka_eyone.services.ProcessOrganizationService;
import net.eyone.kafka_eyone.utils.Base64Util;
import net.eyone.kafka_eyone.utils.ObjectMapperUtil;
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
            try {
                log.info("[OrganizationConsumer] [consumeOrganization] message : {}", message);
                
                Object decodedData = Base64Util.decodeBase64Data(message);
                log.debug("[OrganizationConsumer] [consumeOrganization] decodedData : {}", decodedData);

                OrganizationElastic organization = ObjectMapperUtil.jsonToObject(decodedData, OrganizationElastic.class);
                log.debug("[OrganizationConsumer] [consumeOrganization] organization : {}", organization);

                processOrganization.processSaveOrganization(organization);
                log.info("[OrganizationConsumer] [consumeOrganization] result : success");
            } catch (Exception e) {
                log.error("[OrganizationConsumer] [consumeOrganization] error : {}", e.getMessage());
                throw e;
            }
        };
    }
}
