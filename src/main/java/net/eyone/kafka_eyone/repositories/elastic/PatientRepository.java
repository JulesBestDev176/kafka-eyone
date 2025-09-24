package net.eyone.kafka_eyone.repositories.elastic;

import net.eyone.kafka_eyone.models.elastic.Patient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("elasticPatientRepository")
public interface PatientRepository extends ElasticsearchRepository<Patient, UUID> {
}