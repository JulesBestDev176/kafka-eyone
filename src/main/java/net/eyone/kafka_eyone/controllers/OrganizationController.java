package net.eyone.kafka_eyone.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.dtos.OrganizationElasticDto;
import net.eyone.kafka_eyone.services.interfaces.OrganizationProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationProducerService organizationProducerService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody OrganizationElasticDto organization) {
        organizationProducerService.publish(organization);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}