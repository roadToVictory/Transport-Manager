package org.example.transport.integration.pkp.carrier;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record PkpCarriersResponse(
        Instant generatedAt,
        List<PkpCarrier> carriers
) {

    public record PkpCarrier(
            String code,
            String name,
            LocalDateTime validFrom,
            LocalDateTime validTo
    ) {}
}
