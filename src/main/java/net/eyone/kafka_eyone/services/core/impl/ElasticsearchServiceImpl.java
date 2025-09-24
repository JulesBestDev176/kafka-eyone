package net.eyone.kafka_eyone.services.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.services.core.interfaces.ElasticsearchService;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public <T> T save(T entity) {
        try {
            T saved = elasticsearchOperations.save(entity);
            log.info("[ElasticsearchService] [save] Entité sauvegardée: {}", entity.getClass().getSimpleName());
            return saved;
        } catch (Exception e) {
            log.error("[ElasticsearchService] [save] Erreur lors de la sauvegarde", e);
            throw new RuntimeException("Échec de la sauvegarde Elasticsearch", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T updateStatus(UUID id, String status) {
        try {
            // Déterminer la classe d'entité depuis la stack trace
            String callerClass = getCallerController();
            String entityName = callerClass.substring(callerClass.lastIndexOf('.') + 1).replace("Controller", "");
            String entityClassName = "net.eyone.kafka_eyone.models.elastic." + entityName;
            
            Class<?> entityClass = Class.forName(entityClassName);
            Object entity = elasticsearchOperations.get(id.toString(), entityClass);
            
            if (entity != null) {
                entity.getClass().getMethod("setStatut", String.class).invoke(entity, status);
                T updated = (T) elasticsearchOperations.save(entity);
                log.info("[ElasticsearchService] [updateStatus] Statut mis à jour: {} pour ID: {}", status, id);
                return updated;
            }
            return null;
        } catch (Exception e) {
            log.error("[ElasticsearchService] [updateStatus] Erreur lors de la mise à jour", e);
            throw new RuntimeException("Échec de la mise à jour Elasticsearch", e);
        }
    }
    
    private String getCallerController() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            if (element.getClassName().contains("Controller")) {
                return element.getClassName();
            }
        }
        return "PatientController"; // Défaut
    }
}