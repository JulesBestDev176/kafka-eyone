package net.eyone.kafka_eyone.dtos;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;


@Builder
public record OrganizationElasticDto(
        String id,
        String nom,
        String type,
        String description,
        String telephone,
        String email,
        String adresse,
        Boolean active,
        String statut
) {

}