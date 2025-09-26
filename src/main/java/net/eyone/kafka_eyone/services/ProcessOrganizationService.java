package net.eyone.kafka_eyone.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.organization.OrganizationMongoDto;
import net.eyone.kafka_eyone.mappers.organization.OrganizationMongoMapper;
import net.eyone.kafka_eyone.models.organization.OrganizationElastic;
import net.eyone.kafka_eyone.models.organization.OrganizationMongo;
import net.eyone.kafka_eyone.repositories.organization.OrganizationElasticRepository;
import net.eyone.kafka_eyone.repositories.organization.OrganizationMongoRepository;

import net.eyone.kafka_eyone.utils.JoltUtil;
import org.springframework.stereotype.Service;




@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessOrganizationService {

    private final OrganizationMongoMapper mongoOrganizationMapper;
    private final OrganizationElasticRepository organizationElasticRepository;
    private final OrganizationMongoRepository organizationMongoRepository;

    public void processSaveOrganization(OrganizationElastic organizationElastic) {
        log.debug("[ProcessOrganizationService] [processSaveOrganization] OrganizationElastic : {}", organizationElastic);

        try {
            var savedElastic = organizationElasticRepository.save(organizationElastic);
            log.debug("[ProcessOrganizationService] [processSaveOrganization] savedElastic : {}", savedElastic);

            log.debug("[ProcessOrganizationService] [processSaveOrganization] Organization : {}", savedElastic);
            try {
                var transformedData = JoltUtil.transformationComplet(savedElastic, "spec/organization.json", OrganizationMongoDto.class);
                log.debug("[ProcessOrganizationService] [processSaveOrganization] transformedData : {}", transformedData);

                var savedMongo = mongoOrganizationMapper.toEntity(transformedData);
                log.debug("[ProcessOrganizationService] [processSaveOrganization] savedMongo : {}", savedMongo);

                var mongoResult = organizationMongoRepository.save(savedMongo);
                log.debug("[ProcessOrganizationService] [processSaveOrganization] mongoResult : {}", mongoResult);

                savedElastic.setStatut("success");
                String statut = savedElastic.getStatut();
                log.debug("[ProcessOrganizationService] [processSaveOrganization] statut : {}", statut);

            } catch (Exception e) {
                log.error("[ProcessOrganizationService] [processSaveOrganization] error : {}", e.getMessage());
                savedElastic.setStatut("failed");
                String failedStatut = savedElastic.getStatut();
                log.debug("[ProcessOrganizationService] [processSaveOrganization] failedStatut : {}", failedStatut);
                throw e;
            } finally {
                var finalSave = organizationElasticRepository.save(savedElastic);
                log.debug("[ProcessOrganizationService] [processSaveOrganization] finalSave : {}", finalSave);
            }

            log.debug("[ProcessOrganizationService] [processSaveOrganization] result : success");
        } catch (Exception e) {
            log.error("[ProcessOrganizationService] [processSaveOrganization] globalError : {}", e.getMessage());
            throw e;
        }
    }
}