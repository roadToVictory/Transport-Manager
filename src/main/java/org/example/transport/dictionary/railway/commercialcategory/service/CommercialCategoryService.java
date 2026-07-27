package org.example.transport.dictionary.railway.commercialcategory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.commercialcategory.repository.CommercialCategoryEntity;
import org.example.transport.dictionary.railway.commercialcategory.repository.CommercialCategoryRepository;
import org.example.transport.integration.pkp.commercialcategory.PkpCommercialCategoryClient;
import org.example.transport.integration.pkp.commercialcategory.PkpCommercialCategoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommercialCategoryService {
    private final PkpCommercialCategoryClient pkpCommercialCategoryClient;
    private final CommercialCategoryRepository commercialCategoryRepository;

    public List<PkpCommercialCategoryResponse.PkpCommercialCategory> retrieveCommercialCategories() {
        log.info("Retrieving commercial categories");
        return pkpCommercialCategoryClient.getCommercialCategories()
                .commercialCategories();
    }

    //TODO: Do not execute API queries within a transaction – pass the completed response to the @Transactional method
    @Transactional
    public void synchronizeCommercialCategories() {
        log.info("Synchronizing commercial categories...");

        PkpCommercialCategoryResponse response = pkpCommercialCategoryClient.getCommercialCategories();

        Map<CommercialCategoryKey, CommercialCategoryEntity> existingByKey = commercialCategoryRepository.findAll()
                .stream()
                .collect(Collectors.toMap(this::keyOf, Function.identity()));

        List<CommercialCategoryEntity> categoriesToSave = new ArrayList<>();
        Set<CommercialCategoryKey> receivedKeys = new HashSet<>();

        for (var category : response.commercialCategories()) {
            CommercialCategoryKey key = keyOf(category);

            if (!receivedKeys.add(key)) {
                throw new IllegalStateException("Duplicate commercial category key: " + key);
            }

            String name = category.name() != null ? category.name() : category.code();

            CommercialCategoryEntity existing = existingByKey.remove(key);

            if (existing == null) {
                categoriesToSave.add(
                        new CommercialCategoryEntity(
                                category.code(),
                                name,
                                category.carrierCode(),
                                category.speedCategoryCode()
                        )
                );
            } else {
                existing.update(name, category.speedCategoryCode());
                categoriesToSave.add(existing);
            }
        }

        existingByKey.values().forEach(CommercialCategoryEntity::deactivate);
        categoriesToSave.addAll(existingByKey.values());
        commercialCategoryRepository.saveAll(categoriesToSave);

        log.info("Synchronized '{}' and deactivated '{}' commercial categories from PKP API",
                response.commercialCategories().size(), existingByKey.size());
    }

    private CommercialCategoryKey keyOf(PkpCommercialCategoryResponse.PkpCommercialCategory commercialCategory) {
        return new CommercialCategoryKey(
                commercialCategory.code(),
                commercialCategory.carrierCode()
        );
    }

    private CommercialCategoryKey keyOf(CommercialCategoryEntity commercialCategoryEntity) {
        return new CommercialCategoryKey(
                commercialCategoryEntity.getCode(),
                commercialCategoryEntity.getCarrierCode()
        );
    }

    private record CommercialCategoryKey(
            String code,
            String carrierCode
    ) {}
}
