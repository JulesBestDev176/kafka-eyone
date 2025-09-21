package net.eyone.kafka_eyone.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientRequest {
    private String prenom;
    private String nom;
    private String numero;
    private String sexe;
    private int age;
}