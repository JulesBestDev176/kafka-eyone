package net.eyone.kafka_eyone.repositories;

import net.eyone.kafka_eyone.models.PatientElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientElasticRepository extends ElasticsearchRepository<PatientElastic, String> {
}