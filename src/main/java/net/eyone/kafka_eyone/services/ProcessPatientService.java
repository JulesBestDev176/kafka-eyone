package net.eyone.kafka_eyone.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientMongoDto;
import net.eyone.kafka_eyone.mappers.PatientMongoMapper;
import net.eyone.kafka_eyone.models.PatientElastic;
import net.eyone.kafka_eyone.models.PatientMongo;
import net.eyone.kafka_eyone.repositories.PatientElasticRepository;
import net.eyone.kafka_eyone.repositories.PatientMongoRepository;
import net.eyone.kafka_eyone.utils.TransformationUtil;
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

        Optional<PatientElastic> savedElastic = Optional.of(patientElasticRepository.save(patientElastic));
        log.debug("[ProcessPatientService] [processSavePatient] savedElastic : {}", savedElastic);

        savedElastic.ifPresentOrElse(
                patient -> {
                    log.debug("[ProcessPatientService] [processSavePatient] patient : {}", patient);
                    try {
                        var transformedData = TransformationUtil.TransformationJolt.transformationComplet(patient, "spec/patient.json", PatientMongoDto.class);
                        log.debug("[ProcessPatientService] [processSavePatient] transformedData : {}", transformedData);

                        PatientMongo savedMongo = mongoPatientMapper.toEntity(transformedData);
                        log.debug("[ProcessPatientService] [processSavePatient] savedMongo : {}", savedMongo);

                        patientMongoRepository.save(savedMongo);
                        log.debug("[ProcessPatientService] [processSavePatient] mongoSaved : success");

                        patient.setStatut("success");
                        log.debug("[ProcessPatientService] [processSavePatient] statut : {}", patient.getStatut());

                    } catch (Exception e) {
                        log.error("[ProcessPatientService] [processSavePatient] error : {}", e.getMessage());
                        patient.setStatut("failed");
                        throw e;
                    } finally {
                        patientElasticRepository.save(patient);
                        log.debug("[ProcessPatientService] [processSavePatient] finalSave : success");
                    }
                },
                () -> {
                    log.error("[ProcessPatientService] [processSavePatient] elasticSaveError : failed");
                    throw new RuntimeException("Erreur lors de la sauvegarde dans Elasticsearch");
                }
        );

        log.debug("[ProcessPatientService] [processSavePatient] result : success");
    }
}