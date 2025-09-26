package net.eyone.kafka_eyone.dtos.patient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientMongoDto {
    private String id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String gender;
    private int age;
}