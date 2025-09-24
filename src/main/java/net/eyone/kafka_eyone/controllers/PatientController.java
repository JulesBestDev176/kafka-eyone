package net.eyone.kafka_eyone.controllers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.dtos.elastic.PatientDto;
import net.eyone.kafka_eyone.models.elastic.Patient;
import net.eyone.kafka_eyone.services.ProducerService;
import net.eyone.kafka_eyone.mappers.elastic.PatientMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final ProducerService patientProducerService;
    private final PatientMapper patientMapper;
    
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
        patientProducerService.publish(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}