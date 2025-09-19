package net.eyone.kafka_eyone.controllers;

import lombok.RequiredArgsConstructor;
import net.eyone.kafka_eyone.dtos.PatientRequest;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.PatientProducerService;
import net.eyone.kafka_eyone.services.TransformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST controller exposant l'API Patient.
 * Cas d'usage: lorsqu'un POST est reçu, nous produisons un message dans Kafka via Spring Cloud Stream.
 * Aucun traitement métier lourd n'est implémenté ici pour l'exercice.
 */
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientProducerService patientProducerService;
    private final TransformationService transformationService;


    /**
     * Crée un patient et publie l'événement sur le topic Kafka configuré (isi-patient_topic)
     * via StreamBridge (voir PatientProducerService).
     * @param request payload JSON reçu
     * @return 202 ACCEPTED si le message a été pris en compte
     */
    @PostMapping
    public ResponseEntity<PatientResponse> create(@RequestBody PatientRequest request) {

        if (request.getPrenom() == null || request.getNom() == null) {
            return ResponseEntity.badRequest().build();
        }

        String cheminFichierSpec = "spec/patient.json";

        PatientResponse resultatFinal = transformationService.transformationComplet(request, cheminFichierSpec);

        patientProducerService.envoyerPatient(resultatFinal);

        return new ResponseEntity<>(resultatFinal, HttpStatus.ACCEPTED);
    }
}
