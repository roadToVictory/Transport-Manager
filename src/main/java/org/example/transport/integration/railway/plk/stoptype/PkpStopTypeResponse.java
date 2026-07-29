package org.example.transport.integration.railway.plk.stoptype;

import java.time.Instant;
import java.util.List;

public record PkpStopTypeResponse(
        Instant generatedAt,
        List<PkpStopType> stopTypes
) {
    public record PkpStopType(
            long id,
            String description
    ) {}
}
