package net.eyone.kafka_eyone.services;

import net.eyone.kafka_eyone.dtos.PatientResponse;

/**
 * Port de sortie (interface) responsable de l'émission d'événements Patient sur le bus de messages.
 * Respect des principes SOLID:
 * - SRP: une seule responsabilité, produire des messages
 * - DIP: les consommateurs dépendent de cette abstraction, pas d'une implémentation concrète
 */
public interface PatientProducerService {
    /**
     * Envoie le patient sur le binding configuré (sendPatient-out-0 -> isi-patient_topic).
     * @param patient domaine Patient à publier
     */
    void envoyerPatient(Object patientRequest);
}
