package net.eyone.kafka_eyone.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.mappers.PatientMapper;
import net.eyone.kafka_eyone.models.PatientDocument;
import net.eyone.kafka_eyone.repositories.PatientDocumentRepository;
import net.eyone.kafka_eyone.services.ElasticsearchService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final PatientDocumentRepository patientDocumentRepository;
    private final PatientMapper patientMapper;

    public void indexPatient(PatientResponse patientResponse) {
        try {
            PatientDocument patientDocument = patientMapper.toDocument(patientResponse);

            PatientDocument saved = patientDocumentRepository.save(patientDocument);
            log.info("[ElasticsearchService] [indexPatient] Patient indexé avec ID: {}", saved.getId());

        } catch (Exception e) {
            log.error("[ElasticsearchService] [indexPatient] Erreur lors de l'indexation du patient dans Elasticsearch", e);
            throw new RuntimeException("Échec de l'indexation Elasticsearch", e);
        }
    }
}