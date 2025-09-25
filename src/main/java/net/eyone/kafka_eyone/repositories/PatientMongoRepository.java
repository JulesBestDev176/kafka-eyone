package net.eyone.kafka_eyone.repositories;

import net.eyone.kafka_eyone.models.PatientMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientMongoRepository extends MongoRepository<PatientMongo, String> {
}