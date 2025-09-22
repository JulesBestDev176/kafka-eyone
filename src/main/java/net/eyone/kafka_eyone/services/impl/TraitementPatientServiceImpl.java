package net.eyone.kafka_eyone.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.PatientTransformationService;
import net.eyone.kafka_eyone.services.PatientWebhookService;
import net.eyone.kafka_eyone.services.ElasticsearchService;
import net.eyone.kafka_eyone.services.ObjectMapperService;
import net.eyone.kafka_eyone.services.TraitementPatientService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TraitementPatientServiceImpl implements TraitementPatientService {

    private final PatientWebhookService webhookService;
    private final PatientTransformationService transformationService;
    private final ElasticsearchService elasticsearchService;
    private final ObjectMapperService objectMapperService;

    @Override
    public void traiterPatient(String message) {
        log.info("[TraitementPatientService][traiterPatient] patient: {}", message);
        Object patientRequest = objectMapperService.parseJson(message);

        // Transformation JOLT
        PatientResponse patientTransforme = transformationService.transformationComplet(patientRequest);
        
        // Envoi webhook
        webhookService.send(patientTransforme);
        
        // Stockage dans Elasticsearch
        elasticsearchService.indexPatient(patientTransforme);
        
        log.info("[TraitementPatientService][traiterPatient] patient transformé: {}", patientTransforme);
    }
}