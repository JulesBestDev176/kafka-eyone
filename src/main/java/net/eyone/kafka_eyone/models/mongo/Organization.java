package net.eyone.kafka_eyone.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "organizations")
public class Organization {

    @Id
    private UUID id;

    @Field("resourceType")
    private String resourceType = "Organization";

    @Field("identifier")
    private List<Identifier> identifier;

    @Field("active")
    private Boolean active;

    @Field("type")
    private List<CodeableConcept> type;

    @Field("name")
    private String name;

    @Field("alias")
    private List<String> alias;

    @Field("description")
    private String description;

    @Field("contact")
    private List<ExtendedContactDetail> contact;

    @Field("partOf")
    private Reference partOf;

    @Field("endpoint")
    private List<Reference> endpoint;

    @Field("qualification")
    private List<Qualification> qualification;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Identifier {
        private String system;
        private String value;
        private String use;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CodeableConcept {
        private List<Coding> coding;
        private String text;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Coding {
        private String system;
        private String code;
        private String display;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExtendedContactDetail {
        private CodeableConcept purpose;
        private List<ContactPoint> telecom;
        private Address address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContactPoint {
        private String system;
        private String value;
        private String use;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        private String line;
        private String city;
        private String postalCode;
        private String country;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Reference {
        private String reference;
        private String display;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Qualification {
        private List<Identifier> identifier;
        private CodeableConcept code;
        private Period period;
        private Reference issuer;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Period {
        private String start;
        private String end;
    }
}