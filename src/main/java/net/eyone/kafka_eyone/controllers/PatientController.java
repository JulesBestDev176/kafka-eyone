package net.eyone.kafka_eyone.controllers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.dtos.PatientRequest;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.services.PatientProducerService;
import net.eyone.kafka_eyone.mappers.PatientMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientProducerService patientProducerService;
    private final PatientMapper patientMapper;
    
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PatientRequest request) {
        Patient patient = patientMapper.toPatient(request);
        patientProducerService.publish(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}