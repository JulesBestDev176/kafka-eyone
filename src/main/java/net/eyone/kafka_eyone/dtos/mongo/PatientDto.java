package net.eyone.kafka_eyone.dtos.mongo;

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
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String gender;
    private int age;
}