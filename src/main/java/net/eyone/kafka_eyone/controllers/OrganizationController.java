package net.eyone.kafka_eyone.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.dtos.elastic.OrganizationDto;
import net.eyone.kafka_eyone.models.elastic.Organization;
import net.eyone.kafka_eyone.services.ProducerService;
import net.eyone.kafka_eyone.mappers.elastic.OrganizationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final ProducerService organizationProducerService;
    private final OrganizationMapper elasticOrganizationMapper;
    
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody OrganizationDto organizationDto) {
        Organization organization = elasticOrganizationMapper.toEntity(organizationDto.toMap());
        organizationProducerService.publish(organization);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}