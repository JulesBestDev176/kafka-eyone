package net.eyone.kafka_eyone.services.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.services.PatientTransformationService;
import net.eyone.kafka_eyone.services.TransformationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientTransformationServiceImpl implements PatientTransformationService {

    private final TransformationService transformationService;
    private final ObjectMapper objectMapper;

    @Override
    public PatientResponse transform(Patient patient) {
        try {
            log.info("[PatientTransformationServiceImpl] [transform] Début de la transformation du patient: {}", patient);
            Object transformedData = transformationService.transform(patient, "spec/patient.json");
            log.info("[PatientTransformationServiceImpl] [transform] Données transformées: {}", transformedData);
            PatientResponse response = objectMapper.convertValue(transformedData, PatientResponse.class);
            log.info("[PatientTransformationServiceImpl] [transform] Patient response créée: {}", response);
            return response;
        } catch (Exception e) {
            log.error("[PatientTransformationServiceImpl] [transform] erreur lors de la transformation du patient: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la transformation du patient", e);
        }
    }
}
