package org.example.transport.dictionary.railway.carrier.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.carrier.service.CarrierService;
import org.example.transport.integration.pkp.carrier.PkpCarriersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/carriers")
public class CarrierController {
    private final CarrierService carrierService;

    @GetMapping("/preview")
    public List<PkpCarriersResponse.PkpCarrier> getCarriers() {
        return carrierService.retrieveCarriers();
    }

    @PostMapping("/synchronization")
    public ResponseEntity<Void> synchronizeCarriers() {
        carrierService.synchronizeCarriers();
        return ResponseEntity.noContent().build();
    }

}

