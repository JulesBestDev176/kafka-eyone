package net.eyone.kafka_eyone.dtos.elastic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDto {
    private UUID id;
    private String nom;
    private String type;
    private String description;
    private String telephone;
    private String email;
    private String adresse;
    private Boolean active;
    private String statut;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("nom", nom);
        map.put("type", type);
        map.put("description", description);
        map.put("telephone", telephone);
        map.put("email", email);
        map.put("adresse", adresse);
        map.put("active", active);
        map.put("statut", statut);
        return map;
    }
}