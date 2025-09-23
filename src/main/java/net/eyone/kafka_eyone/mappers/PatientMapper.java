package net.eyone.kafka_eyone.mappers;

import net.eyone.kafka_eyone.dtos.PatientRequest;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.models.PatientDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PatientMapper {

    public Patient toPatient(PatientRequest request) {
        return Patient.builder()
                .id(UUID.randomUUID())
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .numero(request.getNumero())
                .sexe(request.getSexe())
                .age(request.getAge())
                .build();
    }

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