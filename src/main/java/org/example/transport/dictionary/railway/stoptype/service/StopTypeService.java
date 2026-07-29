package org.example.transport.dictionary.railway.stoptype.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.stoptype.repository.StopTypeEntity;
import org.example.transport.dictionary.railway.stoptype.repository.StopTypeRepository;
import org.example.transport.integration.railway.plk.stoptype.PkpStopTypeClient;
import org.example.transport.integration.railway.plk.stoptype.PkpStopTypeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.UnaryOperator.identity;

@Service
@RequiredArgsConstructor
@Slf4j
public class StopTypeService {
    private final PkpStopTypeClient pkpStopTypeClient;
    private final StopTypeRepository stopTypeRepository;

    public List<PkpStopTypeResponse.PkpStopType> retrieveStopTypes() {
        log.debug("Retrieving stop types from PKP API");
        return pkpStopTypeClient.getStopTypes().stopTypes();
    }

    @Transactional
    public void synchronizeStopTypes() {
        log.info("Synchronizing stop types...");

        PkpStopTypeResponse response = pkpStopTypeClient.getStopTypes();
        Map<Long, StopTypeEntity> existingByPkpId = stopTypeRepository.findAll().stream()
                .collect(Collectors.toMap(StopTypeEntity::getPkpId, identity()));

        List<StopTypeEntity> stopTypesToSave = response.stopTypes().stream()
                .map(pkpStopType -> {
                    var existing = existingByPkpId.get(pkpStopType.id());
                    if (existing == null) {
                        return new StopTypeEntity(pkpStopType.id(), pkpStopType.description());
                    }
                    existing.update(pkpStopType.description());
                    return existing;
                }).toList();
        stopTypeRepository.saveAll(stopTypesToSave);
        log.info("Synchronized '{}' stop types from PKP API", stopTypesToSave.size());
    }
}
