package net.eyone.kafka_eyone.utils;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
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

        public static PatientResponse jsonToObjectPatient(Object resultatJolt) {
            log.info("[ObjectMapperService] [jsonToObjectPatient] Resultat: {}", resultatJolt);
            PatientResponse response = objectMapper.convertValue(resultatJolt, PatientResponse.class);
            log.info("[ObjectMapperService] [jsonToObjectPatient] PatientResponse: {}", response);
            return response;
        }

        public static Object parseJson(String message) {
            try {
                // Décoder le Base64 si nécessaire
                String json = isBase64(message) ? decodeBase64(message) : message;
                return objectMapper.readValue(json, Object.class);
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

        public static PatientResponse transformationComplet(Object input) {
            try {

                Map<String, Object> inputJson;
                if (input instanceof String) {
                    inputJson = (Map<String, Object>) ObjectMapperTransformation.parseJson((String) input);
                } else {
                    inputJson = (Map<String, Object>) input;
                }
                log.info("[PatientTransformationService] [transformationComplet] Resultat: {}", inputJson);

                ClassPathResource resource = new ClassPathResource("spec/patient.json");
                InputStream fichierSpec = resource.getInputStream();
                List<Object> spec = JsonUtils.jsonToList(fichierSpec);
                Chainr chainr = Chainr.fromSpec(spec);
                Object resultatJolt = chainr.transform(inputJson);
                if (resultatJolt == null) {
                    throw new RuntimeException("La transformation Jolt a retourné null. Vérifiez la spécification Jolt.");
                }
                log.info("[PatientTransformationService] [transformationComplet] Resultat Jolt: {}", resultatJolt);

                PatientResponse resultat = ObjectMapperTransformation.jsonToObjectPatient(resultatJolt);
                log.info("[PatientTransformationService] [transformationComplet] Resultat: {}", resultat);
                return resultat;

            } catch (Exception e) {
                throw new RuntimeException("Le cycle de transformation a echoue", e);
            }
        }
    }
}
