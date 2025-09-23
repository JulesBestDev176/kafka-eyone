package net.eyone.kafka_eyone.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "patients")
public class PatientDocument {

    @Id
    private UUID id;

    @Field(type = FieldType.Text)
    private String firstname;

    @Field(type = FieldType.Text)
    private String lastname;

    @Field(type = FieldType.Keyword)
    private String phoneNumber;

    @Field(type = FieldType.Keyword)
    private String gender;

    @Field(type = FieldType.Integer)
    private int age;

    @Field(type = FieldType.Date)
    private LocalDateTime timestamp;
}