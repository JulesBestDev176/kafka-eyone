package net.eyone.kafka_eyone.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.services.JsonParsingService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonParsingServiceImpl implements JsonParsingService {

    private final ObjectMapper objectMapper;

    @Override
    public Object parseJson(String json) {
        try {
            return objectMapper.readValue(json, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur parsing JSON", e);
        }
    }
}