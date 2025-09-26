package net.eyone.kafka_eyone.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.patient.PatientMongoDto;
import net.eyone.kafka_eyone.mappers.patient.PatientMongoMapper;
import net.eyone.kafka_eyone.models.patient.PatientElastic;
import net.eyone.kafka_eyone.models.patient.PatientMongo;
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
            PatientElastic savedElastic = patientElasticRepository.save(patientElastic);
            log.debug("[ProcessPatientService] [processSavePatient] savedElastic : {}", savedElastic);

            if (savedElastic != null) {
                log.debug("[ProcessPatientService] [processSavePatient] patient : {}", savedElastic);
                try {
                    PatientMongoDto transformedData = JoltUtil.transformationComplet(savedElastic, "spec/patient.json", PatientMongoDto.class);
                    log.debug("[ProcessPatientService] [processSavePatient] transformedData : {}", transformedData);

                    PatientMongo savedMongo = mongoPatientMapper.toEntity(transformedData);
                    log.debug("[ProcessPatientService] [processSavePatient] savedMongo : {}", savedMongo);

                    PatientMongo mongoResult = patientMongoRepository.save(savedMongo);
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
                    PatientElastic finalSave = patientElasticRepository.save(savedElastic);
                    log.debug("[ProcessPatientService] [processSavePatient] finalSave : {}", finalSave);
                }
            } else {
                log.error("[ProcessPatientService] [processSavePatient] elasticSaveError : failed");
                RuntimeException exception = new RuntimeException("Erreur lors de la sauvegarde dans Elasticsearch");
                log.error("[ProcessPatientService] [processSavePatient] exception : {}", exception.getMessage());
                throw exception;
            }

            log.debug("[ProcessPatientService] [processSavePatient] result : success");
        } catch (Exception e) {
            log.error("[ProcessPatientService] [processSavePatient] globalError : {}", e.getMessage());
            throw e;
        }
    }
}