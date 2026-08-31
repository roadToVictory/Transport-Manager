package org.example.transport.dictionary.railway.citystationgroup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.citystationgroup.repository.CityStationGroupEntity;
import org.example.transport.dictionary.railway.citystationgroup.repository.CityStationGroupRepository;
import org.example.transport.dictionary.railway.station.repository.StationEntity;
import org.example.transport.dictionary.railway.station.repository.StationRepository;
import org.example.transport.integration.railway.plk.city.PkpCitiesResponse;
import org.example.transport.integration.railway.plk.city.PkpCityClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityStationGroupService {
    private final PkpCityClient pkpCityClient;
    private final CityStationGroupRepository cityStationGroupRepository;
    private final StationRepository stationRepository;

    public List<PkpCitiesResponse.PkpCity> retrieveCityStationGroups() {
        log.debug("Retrieving city station groups from PKP API");
        PkpCitiesResponse response = pkpCityClient.getGroupedCities();

        validateResponse(response);

        return response.cities();
    }

    @Transactional
    public void synchronizeCityStationGroups() {
        log.info("Synchronizing city station groups...");

        PkpCitiesResponse response = pkpCityClient.getGroupedCities();

        validateResponse(response);

        Set<Long> requestedStationIds = response.cities().stream()
                .flatMap(city -> city.stationIds().stream())
                .collect(Collectors.toSet());

        Map<Long, StationEntity> stationsByPkpId = stationRepository.findAllByPkpIdIn(requestedStationIds)
                .stream()
                .collect(Collectors.toMap(StationEntity::getPkpId, Function.identity()));

        validateAllStationsExist(requestedStationIds, stationsByPkpId);

        Map<String, CityStationGroupEntity> existingByName = cityStationGroupRepository.findAll().stream()
                .collect(Collectors.toMap(CityStationGroupEntity::getName, Function.identity()));

        List<CityStationGroupEntity> groupsToSave = new ArrayList<>();

        for (var city : response.cities()) {
            Set<StationEntity> stations = city.stationIds()
                    .stream()
                    .map(stationsByPkpId::get)
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            CityStationGroupEntity existing = existingByName.remove(city.name());

            if (existing == null) {
                groupsToSave.add(new CityStationGroupEntity(city.name(), stations));
            } else {
                existing.replaceStations(stations);
                groupsToSave.add(existing);
            }
        }
        existingByName.values().forEach(CityStationGroupEntity::deactivate);

        groupsToSave.addAll(existingByName.values());
        cityStationGroupRepository.saveAll(groupsToSave);

        log.info("Synchronized '{}' city station groups, deactivated: {}", response.cities().size(), existingByName.size());
    }

    private void validateAllStationsExist(Set<Long> requestedStationIds, Map<Long, StationEntity> stationsByPkpId) {
        Set<Long> missingStationIds = new HashSet<>(requestedStationIds);
        missingStationIds.removeAll(stationsByPkpId.keySet());

        if (!missingStationIds.isEmpty()) {
            throw new IllegalStateException("City groups reference stations missing from database: " + missingStationIds);
        }
    }

    private void validateResponse(PkpCitiesResponse response) {
        if (response == null || response.cities() == null || response.cities().isEmpty()) {
            throw new IllegalStateException("PKP returned no city station groups");
        }
    }
}
