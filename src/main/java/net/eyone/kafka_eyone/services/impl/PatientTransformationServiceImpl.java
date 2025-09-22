package net.eyone.kafka_eyone.services.impl;
import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.PatientResponse;
import net.eyone.kafka_eyone.models.Patient;
import net.eyone.kafka_eyone.services.ObjectMapperService;
import net.eyone.kafka_eyone.services.PatientTransformationService;
import net.eyone.kafka_eyone.services.TransformationService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.io.InputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientTransformationServiceImpl implements PatientTransformationService {

    private final TransformationService transformationService;
    private final ObjectMapperService objectMapperService;

    @Override
    public PatientResponse transformationComplet(Object input) {
        try {

            Map<String, Object> inputJson = objectMapperService.objectToJson(input);
            System.out.println("1. Objet converti en json: " + inputJson + "Class: " + inputJson.getClass());

            ClassPathResource resource = new ClassPathResource("spec/patient.json");
            InputStream fichierSpec = resource.getInputStream();
            List<Object> spec = JsonUtils.jsonToList(fichierSpec);
            Chainr chainr = Chainr.fromSpec(spec);
            Object resultatJolt = chainr.transform(inputJson);
            if (resultatJolt == null) {
                throw new RuntimeException("La transformation Jolt a retourné null. Vérifiez la spécification Jolt.");
            }
            System.out.println("2. Resultat de la transformation Jolt : " + resultatJolt + "Class: " + resultatJolt.getClass());

            PatientResponse resultat = objectMapperService.jsonToObjectPatient(resultatJolt);
            System.out.println("3. JSON transforme reconverti en objet final : " + resultat + "Class: " + resultat.getClass());
            System.out.println("Firstname " + resultat.getFirstname());
            return resultat;

        }catch (Exception e) {
            throw new RuntimeException("Le cycle de transformation a echoue", e);
        }
    }
}
