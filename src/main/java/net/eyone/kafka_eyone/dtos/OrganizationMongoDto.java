package net.eyone.kafka_eyone.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record OrganizationMongoDto(
        String id,
        String resourceType,
        Boolean active,
        String type,
        String name,
        List<String> alias,
        String description,
        String telephone,
        String email,
        String address
) {
}