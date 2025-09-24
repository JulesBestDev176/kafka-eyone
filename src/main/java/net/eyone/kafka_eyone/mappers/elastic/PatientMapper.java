package net.eyone.kafka_eyone.mappers.elastic;

import net.eyone.kafka_eyone.dtos.elastic.PatientDto;
import net.eyone.kafka_eyone.models.elastic.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("elasticPatientMapper")
public class PatientMapper {

    public Patient toEntity(PatientDto dto) {
        return Patient.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .numero(dto.getNumero())
                .sexe(dto.getSexe())
                .age(dto.getAge())
                .statut(dto.getStatut())
                .build();
    }

    public PatientDto toDto(Patient entity) {
        return PatientDto.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .numero(entity.getNumero())
                .sexe(entity.getSexe())
                .age(entity.getAge())
                .statut(entity.getStatut())
                .build();
    }
}