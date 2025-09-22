package net.eyone.kafka_eyone.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.ObjectMapperService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObjectMapperServiceImpl implements ObjectMapperService {
    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Object> objectToJson(Object input) {
        return objectMapper.convertValue(input, Map.class);
    }
    @Override
    public PatientResponse jsonToObjectPatient(Object resultatJolt) {
        log.info("[ObjectMapperService] [jsonToObjectPatient] Resultat: {}", resultatJolt);
        PatientResponse response = objectMapper.convertValue(resultatJolt, PatientResponse.class);
        log.info("[ObjectMapperService] [jsonToObjectPatient] PatientResponse: {}", response);
        return response;
    }

    @Override
    public Object parseJson(String json) {
        try {
            return objectMapper.readValue(json, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur parsing JSON", e);
        }
    }
}
