package net.eyone.kafka_eyone.models.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

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

    @Field("identifier")
    private List<Map<String, Object>> identifier;

    @Field("active")
    private Boolean active;

    @Field("type")
    private List<Map<String, Object>> type;

    @Field("name")
    private String name;

    @Field("alias")
    private List<String> alias;

    @Field("description")
    private String description;

    @Field("contact")
    private List<Map<String, Object>> contact;

    @Field("partOf")
    private Map<String, Object> partOf;

    @Field("endpoint")
    private List<Map<String, Object>> endpoint;

    @Field("qualification")
    private List<Map<String, Object>> qualification;
}