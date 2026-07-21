package org.example.transport.dictionary.railway.carrier.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.carrier.repository.CarrierEntity;
import org.example.transport.dictionary.railway.carrier.repository.CarrierRepository;
import org.example.transport.integration.pkp.carrier.PkpCarrierClient;
import org.example.transport.integration.pkp.carrier.PkpCarriersResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarrierService {
    private final PkpCarrierClient pkpCarrierClient;
    private final CarrierRepository carrierRepository;

    public List<PkpCarriersResponse.PkpCarrier> retrieveCarriers() {
        log.debug("Retrieving carriers...");
        return pkpCarrierClient.getCarriers().carriers();
    }

    @Transactional
    public void synchronizeCarriers() {
        log.debug("Synchronizing carriers...");

        PkpCarriersResponse response = pkpCarrierClient.getCarriers();
        List<String> carrierCodes = response.carriers()
                .stream()
                .map(PkpCarriersResponse.PkpCarrier::code)
                .toList();

        Map<String, CarrierEntity> existingByCode = carrierRepository.findAllByCodeIn(carrierCodes)
                .stream()
                .collect(Collectors.toMap(CarrierEntity::getCode, Function.identity()));

        List<CarrierEntity> carriersToSave = response.carriers()
                .stream()
                .map(pkpCarrier -> {
                    boolean isActive = checkIfCarrierIsActive(pkpCarrier);
                    CarrierEntity existing = existingByCode.get(pkpCarrier.code());

                    if (existing == null) {
                        return new CarrierEntity(
                                pkpCarrier.code(),
                                pkpCarrier.name(),
                                pkpCarrier.validFrom(),
                                pkpCarrier.validTo(),
                                isActive
                        );
                    }
                    existing.update(
                            pkpCarrier.name(),
                            pkpCarrier.validFrom(),
                            pkpCarrier.validTo(),
                            isActive
                    );
                    return existing;
                }).toList();

        carrierRepository.saveAll(carriersToSave);
        log.info("Synchronized '{}' carriers from PKP API", carriersToSave.size());
    }

    private boolean checkIfCarrierIsActive(PkpCarriersResponse.PkpCarrier carrier) {
        LocalDateTime now = LocalDateTime.now();
        return !carrier.validFrom().isAfter(now)
                && carrier.validTo().isAfter(now);
    }

}
