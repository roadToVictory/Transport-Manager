package org.example.transport.dictionary.railway.station.api.dto;

import jakarta.validation.constraints.NotNull;

public record ResolveStationsRequest(
        @NotNull Long originStationId,
        @NotNull Long destinationStationId
) {
}
