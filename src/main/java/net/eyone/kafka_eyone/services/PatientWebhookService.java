package net.eyone.kafka_eyone.services;

import net.eyone.kafka_eyone.dtos.PatientResponse;

/**
 * Abstraction de service responsable de l'envoi des événements Patient vers un webhook externe.
 * Respecte SOLID : cette interface définit une seule responsabilité et permet
 * de substituer différentes implémentations sans changer le consommateur (DIP).
 */
public interface PatientWebhookService {
    /**
     * Envoie la charge utile du patient vers un webhook configuré.
     * L'implémentation devrait être résiliente et, si possible, non bloquante.
     */
    void sendToWebhook(String message);
}
