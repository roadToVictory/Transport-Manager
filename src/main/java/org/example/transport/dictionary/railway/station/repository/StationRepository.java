package org.example.transport.dictionary.railway.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface StationRepository extends JpaRepository<StationEntity, Long> {
    List<StationEntity> findAllByPkpIdIn(Collection<Long> pkpIds);
    StationEntity findByName(String name);
    StationEntity findByPkpId(Long pkpId);

    @Query("SELECT station.pkpId FROM StationEntity station")
    List<Long> findAllPkpIds();
}
