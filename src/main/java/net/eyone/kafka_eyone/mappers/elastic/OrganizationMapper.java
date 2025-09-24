package net.eyone.kafka_eyone.mappers.elastic;

import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.mongo.OrganizationDto;
import net.eyone.kafka_eyone.models.elastic.Organization;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Component("elasticOrganizationMapper")
@Slf4j
public class OrganizationMapper {

    public Organization toEntity(Map<String, Object> data) {
        log.info("[OrganizationMapper] Conversion Map vers Organization Elastic");
        
        return Organization.builder()
                .id(data.get("id") != null ? UUID.fromString(data.get("id").toString()) : UUID.randomUUID())
                .nom((String) data.get("nom"))
                .type((String) data.get("type"))
                .description((String) data.get("description"))
                .telephone((String) data.get("telephone"))
                .email((String) data.get("email"))
                .adresse((String) data.get("adresse"))
                .active(data.get("active") != null ? (Boolean) data.get("active") : true)
                .statut((String) data.get("statut"))
                .build();
    }

    public OrganizationDto toDto(Organization entity) {
        log.info("[OrganizationMapper] Conversion Organization Elastic vers DTO");
        
        return OrganizationDto.builder()
                .id(entity.getId())
                .resourceType("Organization")
                .name(entity.getNom())
                .active(entity.getActive())
                .type(entity.getType())
                .description(entity.getDescription())
                .alias(entity.getNom() != null ? Collections.singletonList(entity.getNom()) : null)
                .telephone(entity.getTelephone())
                .email(entity.getEmail())
                .address(entity.getAdresse())
                .build();
    }
}