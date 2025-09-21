package net.eyone.kafka_eyone.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.ElasticsearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    
    @Value("${app.elasticsearch.index}")
    private String indexName;

    public void indexPatient(PatientResponse patient) {
        try {
            IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);
            
            // Créer l'index s'il n'existe pas
            IndexOperations indexOps = elasticsearchOperations.indexOps(indexCoordinates);
            if (!indexOps.exists()) {
                indexOps.create();
                log.info("Index '{}' créé", indexName);
            }

            // Préparer le document avec timestamp
            Map<String, Object> document = new HashMap<>();
            document.put("patient", patient);
            document.put("timestamp", Instant.now().toString());
            document.put("source", "kafka-consumer");

            // Indexer le document
            String documentId = String.valueOf(elasticsearchOperations.save(Document.from(document), indexCoordinates));
            
            log.info("Patient indexé dans Elasticsearch avec l'ID: {}", documentId);
            
        } catch (Exception e) {
            log.error("Erreur lors de l'indexation du patient dans Elasticsearch", e);
            throw new RuntimeException("Échec de l'indexation Elasticsearch", e);
        }
    }
}