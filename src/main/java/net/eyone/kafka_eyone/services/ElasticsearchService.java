package net.eyone.kafka_eyone.services;

import net.eyone.kafka_eyone.dtos.PatientResponse;

public interface ElasticsearchService {
    void indexPatient(PatientResponse patient);
}
