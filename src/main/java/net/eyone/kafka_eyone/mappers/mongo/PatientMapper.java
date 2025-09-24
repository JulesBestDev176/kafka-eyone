package net.eyone.kafka_eyone.mappers.mongo;

import net.eyone.kafka_eyone.dtos.mongo.PatientDto;
import net.eyone.kafka_eyone.models.mongo.Patient;
import org.springframework.stereotype.Component;

@Component("mongoPatientMapper")
public class PatientMapper {

    public Patient toEntity(PatientDto dto) {
        return Patient.builder()
                .id(dto.getId())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .phoneNumber(dto.getPhoneNumber())
                .gender(dto.getGender())
                .age(dto.getAge())
                .build();
    }

    public PatientDto toDto(Patient entity) {
        return PatientDto.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .phoneNumber(entity.getPhoneNumber())
                .gender(entity.getGender())
                .age(entity.getAge())
                .build();
    }
}