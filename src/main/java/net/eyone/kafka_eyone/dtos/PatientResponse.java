package net.eyone.kafka_eyone.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * DTO représentant la réponse contenant les informations d'un patient.
 * Utilisé pour retourner les données vers les clients.
 * 
 * @author Eyone
 * @version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientResponse {

    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String gender;
    private int old;
}