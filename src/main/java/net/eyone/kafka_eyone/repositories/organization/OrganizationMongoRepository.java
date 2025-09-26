package net.eyone.kafka_eyone.repositories.organization;

import net.eyone.kafka_eyone.models.organization.OrganizationMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationMongoRepository extends MongoRepository<OrganizationMongo, String> {
}