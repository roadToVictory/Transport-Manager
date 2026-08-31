package org.example.transport.integration.railway.plk.city;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class PkpCityClient {
    private static final String DICTIONARIES_CITIES = "dictionaries/cities";

    private final RestClient pkpRestClient;

    public PkpCitiesResponse getGroupedCities() {
        log.debug("Retrieving city station groups from PKP API");
        return pkpRestClient.get()
                .uri(DICTIONARIES_CITIES)
                .retrieve()
                .body(PkpCitiesResponse.class);
    }
}
