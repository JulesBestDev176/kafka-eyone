package net.eyone.kafka_eyone.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * DTO représentant une demande de création ou modification d'un patient.
 * Utilisé pour recevoir les données d'entrée depuis les clients.
 * 
 * @author Eyone
 * @version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientRequest {
    private String prenom;
    private String nom;
    private String numero;
    private String sexe;
    private int age;
}