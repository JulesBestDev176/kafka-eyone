package net.eyone.kafka_eyone.controllers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.dtos.PatientRequest;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.services.PatientProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientProducerService patientProducerService;
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PatientRequest request) {
        Patient patient = Patient.builder()
                .id(UUID.randomUUID())
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .numero(request.getNumero())
                .sexe(request.getSexe())
                .age(request.getAge())
                .build();

        patientProducerService.publish(patient);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
