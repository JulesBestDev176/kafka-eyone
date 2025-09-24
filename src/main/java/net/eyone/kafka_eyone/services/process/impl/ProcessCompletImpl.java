package net.eyone.kafka_eyone.services.process.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.ProcessResponse;
import net.eyone.kafka_eyone.dtos.mongo.PatientDto;
import net.eyone.kafka_eyone.services.process.interfaces.ProcessComplet;
import net.eyone.kafka_eyone.services.process.interfaces.ProcessElastic;
import net.eyone.kafka_eyone.services.process.interfaces.ProcessMongo;
import net.eyone.kafka_eyone.utils.TransformationUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessCompletImpl implements ProcessComplet {

    private final ProcessElastic processElastic;
    private final ProcessMongo processMongo;

    @Override
    public void process(String message, String spec) {
        log.info("[ProcessComplet] [process] Traitement du message: {} avec spec: {}", message, spec);
        
        // Transformation du message JSON en objet
        Object parsedObject = TransformationUtil.ObjectMapperTransformation.parseJson(message);
        log.info("[ProcessComplet] [process] Message transformé en objet: {}", parsedObject);
        
        // Sauvegarde dans Elasticsearch avec l'objet parsé
        ProcessResponse<?> elasticResponse = processElastic.saveToElastic(parsedObject);
        log.info("[ProcessComplet] [process] Résultat Elasticsearch: {}", elasticResponse.getMessage());
        
        // Récupérer l'ID depuis la réponse Elasticsearch
        UUID elasticId = null;
        if (elasticResponse.isSuccess() && elasticResponse.getData() != null) {
            try {
                Object savedEntity = elasticResponse.getData();
                elasticId = (UUID) savedEntity.getClass().getMethod("getId").invoke(savedEntity);
                log.info("[ProcessComplet] [process] ID récupéré: {}", elasticId);
            } catch (Exception e) {
                log.error("[ProcessComplet] [process] Erreur lors de la récupération de l'ID", e);
            }
        }
        
        // Sauvegarde dans MongoDB avec l'objet parsé
        if (elasticId != null && elasticResponse.isSuccess()) {
            ProcessResponse<?> mongoResponse = processMongo.saveToMongo(parsedObject, spec, elasticId);
            log.info("[ProcessComplet] [process] Résultat MongoDB: {}", mongoResponse.getMessage());
        } else {
            log.warn("[ProcessComplet] [process] Pas de sauvegarde MongoDB - ID manquant ou échec Elasticsearch");
        }
        
        log.info("[ProcessComplet] [process] Traitement terminé");
    }
}