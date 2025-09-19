package net.eyone.kafka_eyone.services;

import net.eyone.kafka_eyone.dtos.PatientResponse;

import java.util.Map;

public interface ObjectMapperService {
    Map<String, Object> objectToJson(Object input);
    PatientResponse jsonToObjectPatient(Object resultatJolt) ;
}
