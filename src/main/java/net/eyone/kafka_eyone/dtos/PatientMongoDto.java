package net.eyone.kafka_eyone.dtos;

import lombok.Builder;

@Builder
public record PatientMongoDto(
        String id,
        String firstname,
        String lastname,
        String phoneNumber,
        String gender,
        int age
) {
}