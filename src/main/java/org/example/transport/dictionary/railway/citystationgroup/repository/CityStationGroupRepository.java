package org.example.transport.dictionary.railway.citystationgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityStationGroupRepository extends JpaRepository<CityStationGroupEntity, Long> {
    Optional<CityStationGroupEntity> findByNameIgnoreCaseAndActiveTrue(String name);
}
