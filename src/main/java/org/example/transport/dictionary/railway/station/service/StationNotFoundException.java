package org.example.transport.dictionary.railway.station.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(Long id) {
        super("Active railway station not found: id=" + id);
    }
}
