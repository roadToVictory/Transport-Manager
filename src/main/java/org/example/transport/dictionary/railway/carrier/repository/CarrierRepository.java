package org.example.transport.dictionary.railway.carrier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CarrierRepository extends JpaRepository<CarrierEntity, Long> {
    List<CarrierEntity> findAllByCodeIn(Collection<String> codes);
}
