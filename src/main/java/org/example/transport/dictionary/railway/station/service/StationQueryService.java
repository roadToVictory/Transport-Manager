package org.example.transport.dictionary.railway.station.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.station.api.dto.ResolveStationsRequest;
import org.example.transport.dictionary.railway.station.api.dto.ResolvedStationsResponse;
import org.example.transport.dictionary.railway.station.api.dto.StationOptionResponse;
import org.example.transport.dictionary.railway.station.repository.StationEntity;
import org.example.transport.dictionary.railway.station.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StationQueryService {
    private final StationRepository stationRepository;

    public List<StationOptionResponse> getAllActiveStations() {
        log.info("Getting all active stations");
        return stationRepository.findAllByActiveTrueOrderByNameAsc()
                .stream()
                .map(station -> new StationOptionResponse(station.getId(), station.getName()))
                .toList();
    }

    public ResolvedStationsResponse resolveStations(ResolveStationsRequest request) {
        log.info("Resolving stations for originStationId={} and destinationStationId={}", request.originStationId(), request.destinationStationId());
        List<Long> stationIds = List.of(request.originStationId(), request.destinationStationId());

        Map<Long, StationEntity> stationsById = stationRepository.findAllByIdInAndActiveTrue(stationIds)
                .stream()
                .collect(Collectors.toMap(StationEntity::getId, Function.identity()));

        StationEntity origin = getStation(stationsById, request.originStationId());
        StationEntity destination = getStation(stationsById, request.destinationStationId());

        return new ResolvedStationsResponse(origin.getPkpId(), destination.getPkpId());
    }

    private StationEntity getStation(Map<Long, StationEntity> stationsById, long id) {
        StationEntity station = stationsById.get(id);
        if (station == null) {
            throw new StationNotFoundException(id);
        }
        return station;
    }
}
