package org.example.transport.integration.railway.plk.commercialcategory;

import java.time.Instant;
import java.util.List;

public record PkpCommercialCategoryResponse(
        Instant generatedAt,
        List<PkpCommercialCategory> commercialCategories
) {

    public record PkpCommercialCategory(
       String code,
       String name,
       String carrierCode,
       String speedCategoryCode
    ) {}
}
