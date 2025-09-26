package net.eyone.kafka_eyone.mappers.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.eyone.kafka_eyone.dtos.patient.PatientElasticDto;
import net.eyone.kafka_eyone.models.patient.PatientElastic;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientElasticMapper {

    private final ModelMapper modelMapper;

    public PatientElastic toEntity(PatientElasticDto dto) {
        log.debug("[PatientElasticMapper] [toEntity] dto : {}", dto);
        PatientElastic entity = modelMapper.map(dto, PatientElastic.class);
        log.debug("[PatientElasticMapper] [toEntity] entity : {}", entity);
        return entity;
    }

    public PatientElasticDto toDto(PatientElastic entity) {
        log.debug("[PatientElasticMapper] [toDto] entity : {}", entity);
        PatientElasticDto dto = modelMapper.map(entity, PatientElasticDto.class);
        log.debug("[PatientElasticMapper] [toDto] dto : {}", dto);
        return dto;
    }
}