package net.eyone.kafka_eyone.utils;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
public class TransformationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static class ObjectMapperTransformation {

        public static Map<String, Object> objectToJson(Object input) {
            return objectMapper.convertValue(input, Map.class);
        }

        public static <T> T jsonToObject(Object json, Class<T> targetClass) {
            log.debug("[ObjectMapperTransformation] [jsonToObject] json : {}, targetClass : {}", json, targetClass.getSimpleName());
            T result = objectMapper.convertValue(json, targetClass);
            log.debug("[ObjectMapperTransformation] [jsonToObject] result : {}", result);
            return result;
        }

        public static Object parseJson(String message) {
            try {
                // Décoder le Base64 si nécessaire
                String json = isBase64(message) ? decodeBase64(message) : message;
                Object result = objectMapper.readValue(json, Object.class);
                log.info("[ObjectMapperTransformation] [parseJson] Type de retour: {}", result.getClass().getSimpleName());
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Erreur parsing JSON", e);
            }
        }

        private static boolean isBase64(String str) {
            try {
                Base64.getDecoder().decode(str);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        private static String decodeBase64(String base64) {
            return new String(Base64.getDecoder().decode(base64));
        }
    }

    public static class Base64Decoder {
        
        public static Object decodeBase64Data(Object data) {
            if (!(data instanceof String)) {
                log.debug("[Base64Decoder] Données ne sont pas de type String, pas de décodage nécessaire");
                return data;
            }
            
            String originalString = (String) data;
            log.debug("[Base64Decoder] Données String d'origine (longueur: {}): {}", originalString.length(), originalString);
            
            try {
                String base64String = originalString.replaceAll("\"", ""); // Supprimer les guillemets
                log.debug("[Base64Decoder] String après suppression guillemets: {}", base64String);
                
                long decodeStartTime = System.currentTimeMillis();
                byte[] decodedBytes = Base64.getDecoder().decode(base64String);
                String decodedJson = new String(decodedBytes);
                long decodeTime = System.currentTimeMillis() - decodeStartTime;
                
                log.debug("[Base64Decoder] JSON décodé en {} ms: {}", decodeTime, decodedJson);
                
                Object decodedData = ObjectMapperTransformation.parseJson(decodedJson);
                log.info("[Base64Decoder] Données décodées Base64 avec succès: {}", decodedData);
                return decodedData;
            } catch (Exception e) {
                log.warn("[Base64Decoder] Échec décodage Base64 ({}), utilisation données originales", e.getMessage());
                log.debug("[Base64Decoder] Détails erreur décodage:", e);
                return data;
            }
        }
    }

    public static class TransformationJolt {

        public static <T> T transformationComplet(Object object, String spec, Class<T> targetClass) {
            try {
                log.info("[TransformationUtil] [transformationComplet] Resultat: {}", object);

                var input = TransformationUtil.ObjectMapperTransformation.objectToJson(object);

                ClassPathResource resource = new ClassPathResource(spec);
                InputStream fichierSpec = resource.getInputStream();
                List<Object> specList = JsonUtils.jsonToList(fichierSpec);
                Chainr chainr = Chainr.fromSpec(specList);
                Object resultatJolt = chainr.transform(input);
                if (resultatJolt == null) {
                    throw new RuntimeException("La transformation Jolt a retourné null. Vérifiez la spécification Jolt.");
                }
                log.info("[TransformationUtil] [transformationComplet] Resultat Jolt: {}", resultatJolt);

                T resultat = objectMapper.convertValue(resultatJolt, targetClass);
                log.info("[TransformationUtil] [transformationComplet] Resultat: {}", resultat);
                return resultat;

            } catch (Exception e) {
                throw new RuntimeException("Le cycle de transformation a echoue", e);
            }
        }
    }
}
