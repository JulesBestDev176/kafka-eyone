package net.eyone.kafka_eyone.controllers.patient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.dtos.patient.PatientElasticDto;
import net.eyone.kafka_eyone.services.patient.interfaces.PatientProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientProducerService patientProducerService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PatientElasticDto patient) {
        patientProducerService.publish(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}