package net.eyone.kafka_eyone.services;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.models.Patient;

public interface PatientTransformationService {
    PatientResponse transform(Patient patient);
}
