package net.eyone.kafka_eyone.dtos.organization;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class OrganizationMongoDto {
    private String id;
    private String resourceType;
    private List<Map<String, Object>> identifier;
    private Boolean active;
    private List<Map<String, Object>> type;
    private String name;
    private List<String> alias;
    private String description;
    private List<Map<String, Object>> contact;
    private Map<String, Object> partOf;
    private List<Map<String, Object>> endpoint;
    private List<Map<String, Object>> qualification;
}