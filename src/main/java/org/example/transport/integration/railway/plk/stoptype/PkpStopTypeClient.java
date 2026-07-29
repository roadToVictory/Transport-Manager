package org.example.transport.integration.railway.plk.stoptype;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class PkpStopTypeClient {
    private static final String STOP_TYPES = "dictionaries/stop-types";

    private final RestClient pkpRestClient;

    public PkpStopTypeResponse getStopTypes() {
        log.debug("Retrieving stop types from PKP API");
        return pkpRestClient.get()
                .uri(STOP_TYPES)
                .retrieve()
                .body(PkpStopTypeResponse.class);
    }

}
