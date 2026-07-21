package org.example.transport.integration.pkp.carrier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class PkpCarrierClient {
    private static final String CARRIERS = "dictionaries/carriers";

    private final RestClient pkpRestClient;

    public PkpCarriersResponse getCarriers() {
        log.debug("Retrieving carriers from PKP API");
        return pkpRestClient.get()
                .uri(CARRIERS)
                .retrieve()
                .body(PkpCarriersResponse.class);
    }


}
