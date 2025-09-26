package net.eyone.kafka_eyone.mappers.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import net.eyone.kafka_eyone.dtos.mongo.OrganizationDto;
import net.eyone.kafka_eyone.dtos.organization.OrganizationMongoDto;
import net.eyone.kafka_eyone.models.organization.OrganizationMongo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrganizationMongoMapper {
    private final ModelMapper modelMapper;

    public OrganizationMongo toEntity(OrganizationMongoDto dto) {
        log.debug("[OrganizationMongoMapper] [toEntity] dto : {}", dto);
        OrganizationMongo entity = modelMapper.map(dto, OrganizationMongo.class);
        log.debug("[OrganizationMongoMapper] [toEntity] entity : {}", entity);
        return entity;
    }

    public OrganizationMongoDto toDto(OrganizationMongo entity) {
        log.debug("[OrganizationMongoMapper] [toDto] entity : {}", entity);
        OrganizationMongoDto dto = modelMapper.map(entity, OrganizationMongoDto.class);
        log.debug("[OrganizationMongoMapper] [toDto] dto : {}", dto);
        return dto;
    }
}

