package net.eyone.kafka_eyone.models.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "patients")
public class PatientElastic {

    @Id
    private String id;
    
    @Field(type = FieldType.Text)
    private String nom;
    
    @Field(type = FieldType.Text)
    private String prenom;
    
    @Field(type = FieldType.Keyword)
    private String numero;
    
    @Field(type = FieldType.Keyword)
    private String sexe;
    
    @Field(type = FieldType.Integer)
    private int age;
    
    @Field(type = FieldType.Keyword, storeNullValue = true)
    private String statut;
}