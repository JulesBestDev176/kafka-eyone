package net.eyone.kafka_eyone.dtos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.UUID;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientResponse {
    private UUID id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String gender;
    private int age;
}
