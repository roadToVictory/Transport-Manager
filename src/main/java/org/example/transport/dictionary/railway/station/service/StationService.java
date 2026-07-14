package org.example.transport.dictionary.railway.station.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.integration.pkp.station.PkpStationClient;
import org.example.transport.integration.pkp.station.dto.PkpStationsResponse;
import org.example.transport.dictionary.railway.station.repository.StationEntity;
import org.example.transport.dictionary.railway.station.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
@Slf4j
@RequiredArgsConstructor
public class StationService {

    private final PkpStationClient pkpStationClient;
    private final StationRepository stationRepository;

    public void retrieveStations() {
        log.info("Retrieving stations..");
        var response = pkpStationClient.getStations();
        log.info("Retrieved {} stations from PKP API", response.stations().size());
    }

    @Transactional
    public void synchronizeStations() {
        log.info("Synchronizing stations..");

        PkpStationsResponse response = pkpStationClient.getStations();
        List<Long> pkpIds = response.stations().stream().map(PkpStationsResponse.PkpStation::id).toList();
        Map<Long, StationEntity> existingByPkpId = stationRepository.findAllByPkpIdIn(pkpIds)
                        .stream().collect(Collectors.toMap(StationEntity::getPkpId, identity()));

        List<StationEntity> stationsToSave = response.stations().stream()
                        .map(pkpStation -> {
                            var existing = existingByPkpId.get(pkpStation.id());
                            if (existing == null) {
                                return new StationEntity(pkpStation.id(), pkpStation.name());
                            }
                            existing.update(pkpStation.name());
                            return existing;
                        }).toList();
        stationRepository.saveAll(stationsToSave);

        log.info("Synchronized '{}' stations from PKP API", stationsToSave.size());
    }

}
