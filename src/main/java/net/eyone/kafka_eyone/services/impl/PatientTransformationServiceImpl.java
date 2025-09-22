package net.eyone.kafka_eyone.services.impl;
import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.services.ObjectMapperService;
import net.eyone.kafka_eyone.services.PatientTransformationService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.io.InputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientTransformationServiceImpl implements PatientTransformationService {

    private final ObjectMapperService objectMapperService;

    @Override
    public PatientResponse transformationComplet(Object input) {
        try {

            Map<String, Object> inputJson = objectMapperService.objectToJson(input);
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

            PatientResponse resultat = objectMapperService.jsonToObjectPatient(resultatJolt);
            log.info("[PatientTransformationService] [transformationComplet] Resultat: {}", resultat);
            return resultat;

        }catch (Exception e) {
            throw new RuntimeException("Le cycle de transformation a echoue", e);
        }
    }
}
