package org.example.transport.dictionary.railway.station.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.station.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/stations")
public class StationController {
    private final StationService stationService;

    @GetMapping
    public void retrieveStations() {
        stationService.retrieveStations();
    }

    @PostMapping("/synchronization")
    public ResponseEntity<Void> synchronizeStations() {
        stationService.synchronizeStations();
        return ResponseEntity.noContent().build();
    }


}
