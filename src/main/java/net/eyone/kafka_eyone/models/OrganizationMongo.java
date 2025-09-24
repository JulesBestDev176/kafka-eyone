package net.eyone.kafka_eyone.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "organizations")
public class OrganizationMongo {

    @Id
    private String id;

    @Field("resourceType")
    private String resourceType;

    @Field("active")
    private Boolean active;

    @Field("type")
    private String type;

    @Field("name")
    private String name;

    @Field("alias")
    private List<String> alias;

    @Field("description")
    private String description;

    @Field("telephone")
    private String telephone;

    @Field("email")
    private String email;

    @Field("address")
    private String address;
}