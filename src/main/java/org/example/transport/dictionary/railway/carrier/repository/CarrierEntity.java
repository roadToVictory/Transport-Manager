package org.example.transport.dictionary.railway.carrier.repository;

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
import java.time.LocalDateTime;

@Entity
@Table(name = "railway_carriers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarrierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    public CarrierEntity(String code, String name, LocalDateTime validFrom, LocalDateTime validTo, boolean active) {
        this.code = code;
        this.name = name;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.lastModified = Instant.now();
    }

    public void update(String name, LocalDateTime validFrom, LocalDateTime validTo, boolean active) {
        this.name = name;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.lastModified = Instant.now();
    }

    public void deactivate() {
        this.active = false;
        this.lastModified = Instant.now();
    }

}
