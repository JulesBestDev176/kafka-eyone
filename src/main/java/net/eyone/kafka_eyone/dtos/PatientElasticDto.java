package net.eyone.kafka_eyone.dtos;

import lombok.Builder;

@Builder
public record PatientElasticDto(
    String nom,
    String prenom,
    String numero,
    String sexe,
    int age,
    String statut
) {
}