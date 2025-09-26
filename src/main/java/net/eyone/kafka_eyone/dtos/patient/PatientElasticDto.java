package net.eyone.kafka_eyone.dtos.patient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientElasticDto {
    private String nom;
    private String prenom;
    private String numero;
    private String sexe;
    private int age;
    private String statut;
}