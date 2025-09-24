package net.eyone.kafka_eyone.services.process.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.ProcessResponse;
import net.eyone.kafka_eyone.mappers.DtoToModelMapper;
import net.eyone.kafka_eyone.services.core.interfaces.MongoService;
import net.eyone.kafka_eyone.services.process.interfaces.ProcessElastic;
import net.eyone.kafka_eyone.services.process.interfaces.ProcessMongo;
import net.eyone.kafka_eyone.utils.TransformationUtil;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessMongoImpl implements ProcessMongo {

    private final MongoService mongoService;
    private final ProcessElastic processElastic;
    private final DtoToModelMapper dtoToModelMapper;

    @Override
    public ProcessResponse<?> saveToMongo(Object data, String spec, UUID elasticId) {
        // Détection automatique du type basé sur le spec
        Class<?> targetClass = net.eyone.kafka_eyone.dtos.mongo.PatientDto.class;
        if (spec != null && spec.contains("organization")) {
            targetClass = net.eyone.kafka_eyone.dtos.mongo.OrganizationDto.class;
        }
        
        return saveToMongo(data, spec, targetClass, elasticId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ProcessResponse<T> saveToMongo(Object data, String spec, Class<T> targetClass, UUID elasticId) {
        T transformedData = null;
        boolean transformationSuccess = false;
        
        try {
            // Transformation JOLT
            log.info("[ProcessMongo] Données AVANT transformation JOLT: {}", data);
            transformedData = TransformationUtil.TransformationJolt.transformationComplet(data, spec, targetClass);
            log.info("[ProcessMongo] Données APRÈS transformation JOLT: {}", transformedData);
            
            // Définir l'ID UUID après transformation JOLT
            try {
                var field = transformedData.getClass().getDeclaredField("id");
                field.setAccessible(true);
                field.set(transformedData, elasticId);
                log.info("[ProcessMongo] ID défini: {} pour {}", elasticId, transformedData.getClass().getSimpleName());
            } catch (Exception e) {
                log.warn("[ProcessMongo] Impossible de définir l'ID pour {}: {}", transformedData.getClass().getSimpleName(), e.getMessage());
            }
            
            transformationSuccess = true;
            log.info("[ProcessMongo] [saveToMongo] Transformation réussie");
            
            // Conversion DTO vers modèle MongoDB
            Object entityToSave = dtoToModelMapper.mapToModel(transformedData);
            
            // Sauvegarde dans MongoDB (le service détecte automatiquement la collection)
            mongoService.save(entityToSave);
            log.info("[ProcessMongo] [saveToMongo] Entité sauvegardée dans MongoDB: {}", entityToSave.getClass().getSimpleName());
            log.info("[ProcessMongo] Entité sauvegardée avec ID: {}", ((net.eyone.kafka_eyone.models.mongo.Patient) entityToSave).getId());
            
            // Mise à jour du statut Elasticsearch à success
            processElastic.updateElastic(elasticId, "success");
            
        } catch (Exception e) {
            log.error("[ProcessMongo] [saveToMongo] Erreur lors de la transformation", e);
            
            // Mise à jour du statut Elasticsearch à failed
            try {
                processElastic.updateElastic(elasticId, "failed");
            } catch (Exception elasticError) {
                log.error("[ProcessMongo] [saveToMongo] Erreur lors de la mise à jour du statut Elasticsearch", elasticError);
            }
        }
        
        return ProcessResponse.<T>builder()
                .success(transformationSuccess)
                .message(transformationSuccess ? "Transformation et sauvegarde réussies" : "Transformation échouée")
                .data(transformedData)
                .build();
    }
}