package net.eyone.kafka_eyone.repositories;

import net.eyone.kafka_eyone.models.OrganizationElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationElasticRepository extends ElasticsearchRepository<OrganizationElastic, String> {
}