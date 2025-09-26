package net.eyone.kafka_eyone.mappers.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.patient.PatientMongoDto;
import net.eyone.kafka_eyone.models.patient.PatientMongo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientMongoMapper {

    private final ModelMapper modelMapper;

    public PatientMongo toEntity(PatientMongoDto dto) {
        log.debug("[PatientMongoMapper] [toEntity] dto : {}", dto);
        PatientMongo entity = modelMapper.map(dto, PatientMongo.class);
        log.debug("[PatientMongoMapper] [toEntity] entity : {}", entity);
        return entity;
    }

    public PatientMongoDto toDto(PatientMongo entity) {
        log.debug("[PatientMongoMapper] [toDto] entity : {}", entity);
        PatientMongoDto dto = modelMapper.map(entity, PatientMongoDto.class);
        log.debug("[PatientMongoMapper] [toDto] dto : {}", dto);
        return dto;
    }
}