package net.eyone.kafka_eyone.services.impl;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.services.TransformationService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransformationServiceImpl implements TransformationService {
    private final ObjectMapper objectMapper;

    @Override
    public Object transform(Object input, String specPath) throws Exception {
        try {
            InputStream specFile = new ClassPathResource(specPath).getInputStream();
            List<Object> spec = JsonUtils.jsonToList(specFile);

            Chainr chainr = Chainr.fromSpec(spec);

            Object inputJson = objectMapper.convertValue(input, Object.class);

            return chainr.transform(inputJson);
        } catch (Exception e) {
            log.error("[TransformationServiceImpl] [transform] Erreur lors de la transformation JOLT: {}", e.getMessage());
            throw e;
        }
    }
}
