package org.example.transport.integration.railway.plk.city;

import java.time.Instant;
import java.util.List;

public record PkpCitiesResponse(
        Instant generatedAt,
        List<PkpCity> cities
) {
    public record PkpCity(
            String name,
            int stationCount,
            List<Long> stationIds
    ) {}
}
