package net.eyone.kafka_eyone.mappers.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.organization.OrganizationElasticDto;
import net.eyone.kafka_eyone.models.organization.OrganizationElastic;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrganizationElasticMapper {

    private final ModelMapper modelMapper;

    public OrganizationElastic toEntity(OrganizationElasticDto dto) {
        log.debug("[OrganizationElasticMapper] [toEntity] dto : {}", dto);
        OrganizationElastic entity = modelMapper.map(dto, OrganizationElastic.class);
        log.debug("[OrganizationElasticMapper] [toEntity] entity : {}", entity);
        return entity;
    }

    public OrganizationElasticDto toDto(OrganizationElastic entity) {
        log.debug("[OrganizationElasticMapper] [toDto] entity : {}", entity);
        OrganizationElasticDto dto = modelMapper.map(entity, OrganizationElasticDto.class);
        log.debug("[OrganizationElasticMapper] [toDto] dto : {}", dto);
        return dto;
    }
}