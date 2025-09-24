package net.eyone.kafka_eyone.services.process.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.ProcessResponse;
import net.eyone.kafka_eyone.services.core.interfaces.ElasticsearchService;
import net.eyone.kafka_eyone.services.process.interfaces.ProcessElastic;
import net.eyone.kafka_eyone.utils.TransformationUtil;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessElasticImpl implements ProcessElastic {

    private final ElasticsearchService elasticsearchService;

    @Override
    @SuppressWarnings("unchecked")
    public <T> ProcessResponse<T> saveToElastic(T data) {
        try {
            // Déterminer le type à créer depuis la stack trace
            String callerClass = getCallerController();
            T entityToSave = createEntityFromCaller(callerClass, data);
            
            // Sauvegarder avec le service générique
            T saved = elasticsearchService.save(entityToSave);
            
            log.info("[ProcessElastic] [saveToElastic] Entité {} sauvegardée", saved.getClass().getSimpleName());
            return ProcessResponse.<T>builder()
                    .success(true)
                    .message("Entité sauvegardée avec succès")
                    .data(saved)
                    .build();
        } catch (Exception e) {
            log.error("[ProcessElastic] [saveToElastic] Erreur lors de la sauvegarde", e);
            return ProcessResponse.<T>builder()
                    .success(false)
                    .message("Échec de la sauvegarde: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ProcessResponse<T> updateElastic(UUID id, String statut) {
        try {
            T updated = elasticsearchService.updateStatus(id, statut);
            if (updated != null) {
                log.info("[ProcessElastic] [updateElastic] Statut mis à jour pour ID: {}", id);
                return ProcessResponse.<T>builder()
                        .success(true)
                        .message("Statut mis à jour avec succès")
                        .data(updated)
                        .build();
            } else {
                return ProcessResponse.<T>builder()
                        .success(false)
                        .message("Entité non trouvée avec ID: " + id)
                        .data(null)
                        .build();
            }
        } catch (Exception e) {
            log.error("[ProcessElastic] [updateElastic] Erreur lors de la mise à jour", e);
            return ProcessResponse.<T>builder()
                    .success(false)
                    .message("Échec de la mise à jour: " + e.getMessage())
                    .data(null)
                    .build();
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

    @SuppressWarnings("unchecked")
    private <T> T createEntityFromCaller(String callerClass, T sourceData) throws Exception {
        // Extraire le nom simple du controller (ex: PatientController -> Patient)
        String entityName = callerClass.substring(callerClass.lastIndexOf('.') + 1)
                .replace("Controller", "");
        
        // Construire le nom complet de la classe entité Elasticsearch
        String entityClassName = "net.eyone.kafka_eyone.models.elastic." + entityName;
        
        Class<?> entityClass = Class.forName(entityClassName);
        Constructor<?> constructor = entityClass.getDeclaredConstructor();
        Object entity = constructor.newInstance();
        
        // Copier les données depuis sourceData
        log.info("[ProcessElastic] Type de sourceData: {}, Contenu: {}", sourceData.getClass().getSimpleName(), sourceData);
        
        if (sourceData instanceof Map) {
            Map<String, Object> dataMap = (Map<String, Object>) sourceData;
            log.info("[ProcessElastic] Données à copier: {}", dataMap);
            copyMapToEntity(dataMap, entity, entityClass);
        } else if (sourceData instanceof String) {
            // Si c'est encore une String, essayer de la parser
            try {
                Object parsed = TransformationUtil.ObjectMapperTransformation.parseJson((String) sourceData);
                if (parsed instanceof Map) {
                    Map<String, Object> dataMap = (Map<String, Object>) parsed;
                    log.info("[ProcessElastic] Données parsées et à copier: {}", dataMap);
                    copyMapToEntity(dataMap, entity, entityClass);
                } else {
                    log.warn("[ProcessElastic] Parsing n'a pas donné un Map: {}", parsed.getClass().getSimpleName());
                }
            } catch (Exception e) {
                log.error("[ProcessElastic] Erreur lors du parsing de la String: {}", e.getMessage());
            }
        } else {
            log.warn("[ProcessElastic] sourceData n'est ni un Map ni une String: {}", sourceData.getClass().getSimpleName());
        }
        
        // Définir les champs communs (ID, statut)
        try {
            entityClass.getMethod("setId", UUID.class).invoke(entity, UUID.randomUUID());
            entityClass.getMethod("setStatut", String.class).invoke(entity, "pending");
        } catch (Exception e) {
            log.warn("[ProcessElastic] Impossible de définir les champs communs pour {}", entityClass.getSimpleName());
        }
        
        return (T) entity;
    }
    
    private void copyMapToEntity(Map<String, Object> dataMap, Object entity, Class<?> entityClass) {
        dataMap.forEach((key, value) -> {
            if (!"id".equals(key) && !"statut".equals(key) && value != null) {
                try {
                    String setterName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                    // Essayer de trouver le setter avec différents types
                    java.lang.reflect.Method setter = findSetter(entityClass, setterName, value);
                    if (setter != null) {
                        Object convertedValue = convertValue(value, setter.getParameterTypes()[0]);
                        setter.invoke(entity, convertedValue);
                        log.debug("[ProcessElastic] Champ copié: {} = {}", key, value);
                    } else {
                        log.warn("[ProcessElastic] Setter non trouvé pour: {} avec valeur: {} (type: {})", key, value, value.getClass().getSimpleName());
                    }
                } catch (Exception e) {
                    log.warn("[ProcessElastic] Erreur lors de la copie du champ {}: {}", key, e.getMessage());
                }
            }
        });
    }
    
    private java.lang.reflect.Method findSetter(Class<?> entityClass, String setterName, Object value) {
        try {
            // Essayer avec le type exact
            return entityClass.getMethod(setterName, value.getClass());
        } catch (NoSuchMethodException e) {
            // Essayer avec les types primitifs/wrapper
            for (java.lang.reflect.Method method : entityClass.getMethods()) {
                if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    if (isCompatible(value.getClass(), paramType)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }
    
    private boolean isCompatible(Class<?> valueType, Class<?> paramType) {
        if (paramType.isAssignableFrom(valueType)) return true;
        
        // Vérifier les conversions primitives/wrapper
        if ((paramType == int.class && (valueType == Integer.class || valueType == Number.class)) ||
            (paramType == Integer.class && (valueType == Integer.class || valueType == Number.class)) ||
            (paramType == String.class && valueType == String.class) ||
            (paramType == long.class && (valueType == Long.class || valueType == Number.class)) ||
            (paramType == double.class && (valueType == Double.class || valueType == Number.class))) {
            return true;
        }
        return false;
    }
    
    private Object convertValue(Object value, Class<?> targetType) {
        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }
        
        // Conversions numériques
        if (targetType == int.class || targetType == Integer.class) {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
        }
        
        if (targetType == long.class || targetType == Long.class) {
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
        }
        
        if (targetType == double.class || targetType == Double.class) {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
        }
        
        return value;
    }
}