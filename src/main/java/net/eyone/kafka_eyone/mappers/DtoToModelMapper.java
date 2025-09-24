package net.eyone.kafka_eyone.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DtoToModelMapper {

    public Object mapToModel(Object dto) {
        String className = dto.getClass().getSimpleName();
        
        if (!className.endsWith("Dto")) {
            return dto; // Pas un DTO, retourner tel quel
        }
        
        try {
            // Déterminer le nom du modèle (PatientDto -> Patient)
            String modelName = className.replace("Dto", "");
            String modelPackage = "net.eyone.kafka_eyone.models.mongo." + modelName;
            Class<?> modelClass = Class.forName(modelPackage);
            
            // Créer une instance du modèle
            Object modelInstance = modelClass.getDeclaredConstructor().newInstance();
            
            // Copier tous les champs du DTO vers le modèle
            var dtoFields = dto.getClass().getDeclaredFields();
            for (var field : dtoFields) {
                try {
                    field.setAccessible(true);
                    var value = field.get(dto);
                    var modelField = modelClass.getDeclaredField(field.getName());
                    modelField.setAccessible(true);
                    modelField.set(modelInstance, value);
                } catch (Exception e) {
                    log.debug("[DtoToModelMapper] Champ {} non trouvé dans le modèle", field.getName());
                }
            }
            
            log.info("[DtoToModelMapper] DTO {} converti vers modèle {}", className, modelName);
            return modelInstance;
            
        } catch (Exception e) {
            log.warn("[DtoToModelMapper] Impossible de convertir {} vers modèle: {}", className, e.getMessage());
            return dto; // Retourner le DTO original en cas d'erreur
        }
    }
}