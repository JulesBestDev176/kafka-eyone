package net.eyone.kafka_eyone.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.patient.PatientMongoDto;
import net.eyone.kafka_eyone.mappers.patient.PatientMongoMapper;
import net.eyone.kafka_eyone.models.patient.PatientElastic;
import net.eyone.kafka_eyone.repositories.patient.PatientElasticRepository;
import net.eyone.kafka_eyone.repositories.patient.PatientMongoRepository;
import net.eyone.kafka_eyone.utils.JoltUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessPatientService {

    private final PatientMongoMapper mongoPatientMapper;
    private final PatientElasticRepository patientElasticRepository;
    private final PatientMongoRepository patientMongoRepository;

    public void processSavePatient(PatientElastic patientElastic) {
        log.debug("[ProcessPatientService] [processSavePatient] patientElastic : {}", patientElastic);

        try {
            var savedElastic = patientElasticRepository.save(patientElastic);
            log.debug("[ProcessPatientService] [processSavePatient] savedElastic : {}", savedElastic);

            log.debug("[ProcessPatientService] [processSavePatient] patient : {}", savedElastic);
            try {
                var transformedData = JoltUtil.transformationComplet(savedElastic, "spec/patient.json", PatientMongoDto.class);
                log.debug("[ProcessPatientService] [processSavePatient] transformedData : {}", transformedData);

                var savedMongo = mongoPatientMapper.toEntity(transformedData);
                log.debug("[ProcessPatientService] [processSavePatient] savedMongo : {}", savedMongo);

                var mongoResult = patientMongoRepository.save(savedMongo);
                log.debug("[ProcessPatientService] [processSavePatient] mongoResult : {}", mongoResult);

                savedElastic.setStatut("success");
                String statut = savedElastic.getStatut();
                log.debug("[ProcessPatientService] [processSavePatient] statut : {}", statut);

            } catch (Exception e) {
                log.error("[ProcessPatientService] [processSavePatient] error : {}", e.getMessage());
                savedElastic.setStatut("failed");
                String failedStatut = savedElastic.getStatut();
                log.debug("[ProcessPatientService] [processSavePatient] failedStatut : {}", failedStatut);
                throw e;
            } finally {
                var finalSave = patientElasticRepository.save(savedElastic);
                log.debug("[ProcessPatientService] [processSavePatient] finalSave : {}", finalSave);
            }

            log.debug("[ProcessPatientService] [processSavePatient] result : success");
        } catch (Exception e) {
            log.error("[ProcessPatientService] [processSavePatient] globalError : {}", e.getMessage());
            throw e;
        }
    }
}