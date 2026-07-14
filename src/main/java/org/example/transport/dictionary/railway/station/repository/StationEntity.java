package org.example.transport.dictionary.railway.station.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "stations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long pkpId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private Instant lastModified;

    public StationEntity(Long pkpId, String name) {
        this.pkpId = pkpId;
        this.name = name;
        this.lastModified = Instant.now();
    }

    public void update(String name) {
        this.name = name;
        this.active = true;
        this.lastModified = Instant.now();
    }

    public void deactivate() {
        this.active = false;
        this.lastModified = Instant.now();
    }

}
