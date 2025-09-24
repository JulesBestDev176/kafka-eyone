package net.eyone.kafka_eyone.dtos.elastic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {

    private UUID id;
    private String nom;
    private String prenom;
    private String numero;
    private String sexe;
    private int age;
    private String statut;
}