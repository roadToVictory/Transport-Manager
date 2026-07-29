package org.example.transport.dictionary.railway.stoptype.repository;

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
@Table(name = "railway_stop_types")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StopTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pkp_id", nullable = false, unique = true)
    private Long pkpId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private Instant lastModified;

    public StopTypeEntity(Long pkpId, String description) {
        this.pkpId = pkpId;
        this.description = description;
        this.lastModified = Instant.now();
    }

    public void update(String description) {
        this.description = description;
        this.active = true;
        this.lastModified = Instant.now();
    }

    public void deactivate() {
        if (active) {
            this.active = false;
            this.lastModified = Instant.now();
        }
    }
}
