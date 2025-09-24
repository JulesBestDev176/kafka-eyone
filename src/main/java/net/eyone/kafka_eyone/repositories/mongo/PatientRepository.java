package net.eyone.kafka_eyone.repositories.mongo;

import net.eyone.kafka_eyone.models.mongo.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("mongoPatientRepository")
public interface PatientRepository extends MongoRepository<Patient, UUID> {
}