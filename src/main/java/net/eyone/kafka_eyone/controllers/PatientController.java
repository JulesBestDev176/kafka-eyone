package net.eyone.kafka_eyone.controllers;

import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.services.PatientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    @PostMapping
    public Patient create(@RequestBody Patient patient) {
        return patient;
    }
}
