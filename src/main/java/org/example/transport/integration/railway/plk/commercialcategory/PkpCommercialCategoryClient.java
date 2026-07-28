package org.example.transport.integration.railway.plk.commercialcategory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class PkpCommercialCategoryClient {
    private static final String COMMERCIAL_CATEGORIES = "dictionaries/commercial-categories";

    private final RestClient pkpRestClient;

    public PkpCommercialCategoryResponse getCommercialCategories() {
        log.debug("Retrieving commercial categories from PKP API");
        return pkpRestClient.get()
                .uri(COMMERCIAL_CATEGORIES)
                .retrieve()
                .body(PkpCommercialCategoryResponse.class);
    }

}
