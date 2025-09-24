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

    public static class TransformationJolt {

        public static <T> T transformationComplet(Object input, String spec, Class<T> targetClass) {
            try {

                Map<String, Object> inputJson;
                if (input instanceof String) {
                    inputJson = (Map<String, Object>) ObjectMapperTransformation.parseJson((String) input);
                } else {
                    inputJson = (Map<String, Object>) input;
                }
                log.info("[PatientTransformationService] [transformationComplet] Resultat: {}", inputJson);

                ClassPathResource resource = new ClassPathResource(spec);
                InputStream fichierSpec = resource.getInputStream();
                List<Object> specList = JsonUtils.jsonToList(fichierSpec);
                Chainr chainr = Chainr.fromSpec(specList);
                Object resultatJolt = chainr.transform(inputJson);
                if (resultatJolt == null) {
                    throw new RuntimeException("La transformation Jolt a retourné null. Vérifiez la spécification Jolt.");
                }
                log.info("[PatientTransformationService] [transformationComplet] Resultat Jolt: {}", resultatJolt);

                T resultat = objectMapper.convertValue(resultatJolt, targetClass);
                log.info("[PatientTransformationService] [transformationComplet] Resultat: {}", resultat);
                return resultat;

            } catch (Exception e) {
                throw new RuntimeException("Le cycle de transformation a echoue", e);
            }
        }
    }
}
