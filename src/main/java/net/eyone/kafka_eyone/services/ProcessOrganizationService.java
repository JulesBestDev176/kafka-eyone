package net.eyone.kafka_eyone.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eyone.kafka_eyone.dtos.OrganizationMongoDto;
import net.eyone.kafka_eyone.mappers.OrganizationMongoMapper;
import net.eyone.kafka_eyone.models.OrganizationElastic;
import net.eyone.kafka_eyone.models.OrganizationMongo;
import net.eyone.kafka_eyone.repositories.OrganizationElasticRepository;
import net.eyone.kafka_eyone.repositories.OrganizationMongoRepository;

import net.eyone.kafka_eyone.utils.TransformationUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessOrganizationService {

    private final OrganizationMongoMapper mongoOrganizationMapper;
    private final OrganizationElasticRepository organizationElasticRepository;
    private final OrganizationMongoRepository organizationMongoRepository;

    public void processSaveOrganization(OrganizationElastic organizationElastic) {
        log.debug("[ProcessOrganizationService] [processSaveOrganization] OrganizationElastic : {}", organizationElastic);

        var savedElastic = Optional.of(organizationElasticRepository.save(organizationElastic));
        log.debug("[ProcessOrganizationService] [processSaveOrganization] savedElastic : {}", savedElastic);

        savedElastic.ifPresentOrElse(
                organization -> {
                    log.debug("[ProcessOrganizationService] [processSaveOrganization] Organization : {}", organization);
                    try {
                        var transformedData = TransformationUtil.TransformationJolt.transformationComplet(organization, "spec/organization.json", OrganizationMongoDto.class);
                        log.debug("[ProcessOrganizationService] [processSaveOrganization] transformedData : {}", transformedData);

                        OrganizationMongo savedMongo = mongoOrganizationMapper.toEntity(transformedData);
                        log.debug("[ProcessOrganizationService] [processSaveOrganization] savedMongo : {}", savedMongo);

                        organizationMongoRepository.save(savedMongo);
                        log.debug("[ProcessOrganizationService] [processSaveOrganization] mongoSaved : success");

                        organization.setStatut("success");
                        log.debug("[ProcessOrganizationService] [processSaveOrganization] statut : {}", organization.getStatut());

                    } catch (Exception e) {
                        log.error("[ProcessOrganizationService] [processSaveOrganization] error : {}", e.getMessage());
                        organization.setStatut("failed");
                        throw e;
                    } finally {
                        organizationElasticRepository.save(organization);
                        log.debug("[ProcessOrganizationService] [processSaveOrganization] finalSave : success");
                    }
                },
                () -> {
                    log.error("[ProcessOrganizationService] [processSaveOrganization] elasticSaveError : failed");
                    throw new RuntimeException("Erreur lors de la sauvegarde dans Elasticsearch");
                }
        );

        log.debug("[ProcessOrganizationService] [processSaveOrganization] result : success");
    }
}