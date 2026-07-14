package org.example.transport.integration.pkp.station.dto;

import java.time.Instant;
import java.util.List;

public record PkpStationsResponse(
        Instant generatedAt,
        List<PkpStation> stations
) {

    public record PkpStation(
            long id,
            String name
    ) {}
}
