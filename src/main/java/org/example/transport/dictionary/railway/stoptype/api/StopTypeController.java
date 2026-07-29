package org.example.transport.dictionary.railway.stoptype.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.stoptype.service.StopTypeService;
import org.example.transport.integration.railway.plk.stoptype.PkpStopTypeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/stop-types")
public class StopTypeController {

    private final StopTypeService stopTypeService;

    @GetMapping("/preview")
    public List<PkpStopTypeResponse.PkpStopType> getStopTypes() {
        return stopTypeService.retrieveStopTypes();
    }

    @PostMapping("/synchronization")
    public ResponseEntity<Void> synchronizeStopTypes() {
        stopTypeService.synchronizeStopTypes();
        return ResponseEntity.noContent().build();
    }
}
