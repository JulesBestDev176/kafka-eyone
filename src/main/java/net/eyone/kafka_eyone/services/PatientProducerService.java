package net.eyone.kafka_eyone.services;

import net.eyone.kafka_eyone.models.Patient;

public interface PatientProducerService {
    /**
     * Envoie le patient sur le binding configuré (sendPatient-out-0 -> isi-patient_topic).
     * @param patient domaine Patient à publier
     */
    void publish(Patient patient);
}
