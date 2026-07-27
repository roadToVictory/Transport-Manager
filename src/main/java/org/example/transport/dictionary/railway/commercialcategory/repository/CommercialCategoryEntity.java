package org.example.transport.dictionary.railway.commercialcategory.repository;

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
@Table(name = "railway_commercial_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommercialCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String carrierCode;

    @Column(nullable = false)
    private String speedCategoryCode;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private Instant lastModified;

    public CommercialCategoryEntity(String code, String name, String carrierCode, String speedCategoryCode) {
        this.code = code;
        this.name = name;
        this.carrierCode = carrierCode;
        this.speedCategoryCode = speedCategoryCode;
        this.lastModified = Instant.now();
    }

    public void update(String name, String speedCategoryCode) {
        this.name = name;
        this.speedCategoryCode = speedCategoryCode;
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
