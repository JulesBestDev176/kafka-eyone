package net.eyone.kafka_eyone.repositories.organization;

import net.eyone.kafka_eyone.models.organization.OrganizationElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationElasticRepository extends ElasticsearchRepository<OrganizationElastic, String> {
}