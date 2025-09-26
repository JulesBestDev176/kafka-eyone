package net.eyone.kafka_eyone.dtos.organization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationElasticDto {
    private String id;
    private String nom;
    private String type;
    private String description;
    private String telephone;
    private String email;
    private String adresse;
    private Boolean active;
    private String statut;
}