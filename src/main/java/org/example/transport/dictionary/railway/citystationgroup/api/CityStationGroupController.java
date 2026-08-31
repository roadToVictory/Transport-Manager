package org.example.transport.dictionary.railway.citystationgroup.api;

import lombok.RequiredArgsConstructor;
import org.example.transport.dictionary.railway.citystationgroup.service.CityStationGroupService;
import org.example.transport.integration.railway.plk.city.PkpCitiesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/city-station-groups")
public class CityStationGroupController {
    private final CityStationGroupService cityStationGroupService;

    @GetMapping("/preview")
    public List<PkpCitiesResponse.PkpCity> retrieveCityStationGroups() {
        return cityStationGroupService.retrieveCityStationGroups();
    }

    @PostMapping("/synchronization")
    public ResponseEntity<Void> synchronizeCityStationGroups() {
        cityStationGroupService.synchronizeCityStationGroups();
        return ResponseEntity.noContent().build();
    }
}
