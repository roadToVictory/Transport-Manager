package org.example.transport.dictionary.railway.station.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.transport.dictionary.railway.station.api.dto.ResolveStationsRequest;
import org.example.transport.dictionary.railway.station.api.dto.ResolvedStationsResponse;
import org.example.transport.dictionary.railway.station.api.dto.StationOptionResponse;
import org.example.transport.dictionary.railway.station.service.StationQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/v1/railway/stations")
@RequiredArgsConstructor
public class StationQueryController {
    private final StationQueryService stationQueryService;

    @GetMapping
    public List<StationOptionResponse> getAllActiveStations() {
        return stationQueryService.getAllActiveStations();
    }

    @PostMapping("/resolve")
    public ResolvedStationsResponse resolveStations(@Valid @RequestBody ResolveStationsRequest request) {
        return stationQueryService.resolveStations(request);
    }

}
