package net.eyone.kafka_eyone.services;

import net.eyone.kafka_eyone.dtos.PatientResponse;

public interface TransformationService {
    PatientResponse transformationComplet(Object input, String cheminSpec);
}
