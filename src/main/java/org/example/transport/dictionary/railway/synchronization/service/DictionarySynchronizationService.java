package org.example.transport.dictionary.railway.synchronization.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.carrier.service.CarrierService;
import org.example.transport.dictionary.railway.citystationgroup.service.CityStationGroupService;
import org.example.transport.dictionary.railway.commercialcategory.service.CommercialCategoryService;
import org.example.transport.dictionary.railway.station.service.StationService;
import org.example.transport.dictionary.railway.stoptype.service.StopTypeService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class DictionarySynchronizationService {
    private final CarrierService carrierService;
    private final CityStationGroupService cityStationGroupService;
    private final CommercialCategoryService commercialCategoryService;
    private final StationService stationService;
    private final StopTypeService stopTypeService;

    public void synchronizeAllDictionaries() {
        Instant startTime = Instant.now();

        log.info("Starting dictionary synchronization...");

        stationService.synchronizeStations();
        cityStationGroupService.synchronizeCityStationGroups();
        carrierService.synchronizeCarriers();
        commercialCategoryService.synchronizeCommercialCategories();
        stopTypeService.synchronizeStopTypes();

        Duration duration = Duration.between(startTime, Instant.now());

        log.info("Dictionary synchronization completed in {} ms.", duration.toMillis());
    }
}
