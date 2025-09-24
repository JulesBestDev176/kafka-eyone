package net.eyone.kafka_eyone.dtos.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDto {
    private UUID id;
    private String resourceType;
    private String name;
    private Boolean active;
    private String type;
    private String description;
    private List<String> alias;
    private String telephone;
    private String email;
    private String address;
}