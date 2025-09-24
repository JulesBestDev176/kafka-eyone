package net.eyone.kafka_eyone.mappers.mongo;

import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.mongo.OrganizationDto;
import net.eyone.kafka_eyone.models.mongo.Organization;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("mongoOrganizationMapper")
@Slf4j
public class OrganizationMapper {

    public Organization toEntity(OrganizationDto dto) {
        log.info("[OrganizationMapper] Conversion OrganizationDto vers Organization MongoDB");
        
        // Création des contacts
        List<Organization.ExtendedContactDetail> contacts = null;
        if (dto.getTelephone() != null || dto.getEmail() != null) {
            Organization.ContactPoint phoneContact = dto.getTelephone() != null ?
                Organization.ContactPoint.builder()
                    .system("phone")
                    .value(dto.getTelephone())
                    .use("work")
                    .build() : null;
            
            Organization.ContactPoint emailContact = dto.getEmail() != null ?
                Organization.ContactPoint.builder()
                    .system("email")
                    .value(dto.getEmail())
                    .use("work")
                    .build() : null;
            
            List<Organization.ContactPoint> telecom = List.of(phoneContact, emailContact)
                .stream().filter(c -> c != null).toList();
            
            Organization.Address address = dto.getAddress() != null ?
                Organization.Address.builder()
                    .line(dto.getAddress())
                    .build() : null;
            
            contacts = Collections.singletonList(
                Organization.ExtendedContactDetail.builder()
                    .telecom(telecom)
                    .address(address)
                    .build()
            );
        }
        
        // Création du type
        List<Organization.CodeableConcept> types = null;
        if (dto.getType() != null) {
            types = Collections.singletonList(
                Organization.CodeableConcept.builder()
                    .text(dto.getType())
                    .build()
            );
        }
        
        return Organization.builder()
                .id(dto.getId())
                .resourceType("Organization")
                .name(dto.getName())
                .active(dto.getActive())
                .type(types)
                .description(dto.getDescription())
                .alias(dto.getAlias())
                .contact(contacts)
                .build();
    }

    public OrganizationDto toDto(Organization entity) {
        log.info("[OrganizationMapper] Conversion Organization MongoDB vers DTO");
        
        String telephone = null;
        String email = null;
        String address = null;
        
        if (entity.getContact() != null && !entity.getContact().isEmpty()) {
            Organization.ExtendedContactDetail contact = entity.getContact().get(0);
            if (contact.getTelecom() != null) {
                telephone = contact.getTelecom().stream()
                    .filter(t -> "phone".equals(t.getSystem()))
                    .map(Organization.ContactPoint::getValue)
                    .findFirst().orElse(null);
                email = contact.getTelecom().stream()
                    .filter(t -> "email".equals(t.getSystem()))
                    .map(Organization.ContactPoint::getValue)
                    .findFirst().orElse(null);
            }
            if (contact.getAddress() != null) {
                address = contact.getAddress().getLine();
            }
        }
        
        String type = entity.getType() != null && !entity.getType().isEmpty() ?
            entity.getType().get(0).getText() : null;
        
        return OrganizationDto.builder()
                .id(entity.getId())
                .resourceType(entity.getResourceType())
                .name(entity.getName())
                .active(entity.getActive())
                .type(type)
                .description(entity.getDescription())
                .alias(entity.getAlias())
                .telephone(telephone)
                .email(email)
                .address(address)
                .build();
    }
}