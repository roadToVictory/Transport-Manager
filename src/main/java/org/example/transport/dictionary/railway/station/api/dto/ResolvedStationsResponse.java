package org.example.transport.dictionary.railway.station.api.dto;

public record ResolvedStationsResponse(
        Long originPkpStationId,
        Long destinationPkpStationId
) {
}
