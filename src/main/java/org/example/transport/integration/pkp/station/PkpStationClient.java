package org.example.transport.integration.pkp.station;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.integration.pkp.station.dto.PkpStationsResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class PkpStationClient {
    private static final String DICTIONARIES_STATIONS = "dictionaries/stations";
    private static final int MAX_PAGE_SIZE = 5000;

    private final RestClient pkpRestClient;

    public PkpStationsResponse getStations() {
        log.debug("Retrieving stations from PKP API");
        return pkpRestClient.get()
                .uri(uriBuilder -> uriBuilder.path(DICTIONARIES_STATIONS)
                        .queryParam("pageSize", MAX_PAGE_SIZE)
                        .build())
                .retrieve()
                .body(PkpStationsResponse.class);
    }

}
