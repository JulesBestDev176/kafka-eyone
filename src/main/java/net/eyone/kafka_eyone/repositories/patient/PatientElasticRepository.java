package net.eyone.kafka_eyone.repositories.patient;

import net.eyone.kafka_eyone.models.patient.PatientElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientElasticRepository extends ElasticsearchRepository<PatientElastic, String> {
}