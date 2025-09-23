package net.eyone.kafka_eyone.mappers;

import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.models.PatientDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PatientMapper {

    public PatientDocument toDocument(PatientResponse patientResponse) {
        return PatientDocument.builder()
                .id(patientResponse.getId())
                .firstname(patientResponse.getFirstname())
                .lastname(patientResponse.getLastname())
                .phoneNumber(patientResponse.getPhoneNumber())
                .gender(patientResponse.getGender())
                .age(patientResponse.getAge())
                .timestamp(LocalDateTime.now())
                .build();
    }
}