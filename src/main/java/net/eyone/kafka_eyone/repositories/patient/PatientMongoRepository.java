package net.eyone.kafka_eyone.repositories.patient;

import net.eyone.kafka_eyone.models.patient.PatientMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientMongoRepository extends MongoRepository<PatientMongo, String> {
}