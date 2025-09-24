package net.eyone.kafka_eyone.services;

public interface ProducerService {
    /**
     * Envoie l'entité sur le binding configuré (sendPatient-out-0 -> isi-patient_topic).
     * @param entity entité à publier
     */
    <T> void publish(T entity);
}
