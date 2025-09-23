package net.eyone.kafka_eyone.repositories;

import net.eyone.kafka_eyone.models.PatientDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientDocumentRepository extends ElasticsearchRepository<PatientDocument, UUID> {
}