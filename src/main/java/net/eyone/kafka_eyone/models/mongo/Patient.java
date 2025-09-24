package net.eyone.kafka_eyone.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "patients")
public class Patient {

    @Id
    private UUID id;

    @Field("firstname")
    private String firstname;

    @Field("lastname")
    private String lastname;

    @Field("phoneNumber")
    private String phoneNumber;

    @Field("gender")
    private String gender;

    @Field("age")
    private int age;
}