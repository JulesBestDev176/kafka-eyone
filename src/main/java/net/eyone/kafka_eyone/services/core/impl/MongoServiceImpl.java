package net.eyone.kafka_eyone.services.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.services.core.interfaces.MongoService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongoServiceImpl implements MongoService {

    private final MongoTemplate mongoTemplate;

    @Override
    public <T> void save(T entity) {
        try {
            log.info("[MongoService] Tentative de sauvegarde: {}", entity);
            log.info("[MongoService] Base de données utilisée: {}", mongoTemplate.getDb().getName());
            
            T savedEntity = mongoTemplate.save(entity);
            log.info("[MongoService] [save] Entité sauvegardée: {}", savedEntity);
            
            // Vérification immédiate
            long count = mongoTemplate.getCollection("patients").countDocuments();
            log.info("[MongoService] Nombre total de documents dans patients: {}", count);
            
            // Lister tous les documents pour debug
            mongoTemplate.findAll(entity.getClass()).forEach(doc -> 
                log.info("[MongoService] Document trouvé: {}", doc)
            );
        } catch (Exception e) {
            log.error("[MongoService] [save] Erreur lors de la sauvegarde", e);
            throw new RuntimeException("Échec de la sauvegarde MongoDB", e);
        }
    }
}