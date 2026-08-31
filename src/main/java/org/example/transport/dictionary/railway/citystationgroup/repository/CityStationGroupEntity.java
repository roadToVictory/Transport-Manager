package org.example.transport.dictionary.railway.citystationgroup.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.transport.dictionary.railway.station.repository.StationEntity;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "railway_city_station_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityStationGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "railway_city_station_group_members",
            joinColumns = @JoinColumn(name = "city_station_group_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id", unique = true)
    )
    private Set<StationEntity> stations = new HashSet<>();

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private Instant lastModified;

    public CityStationGroupEntity(String name, Collection<StationEntity> stations) {
        this.name = name;
        replaceStations(stations);
    }

    public void replaceStations(Collection<StationEntity> stations) {
        this.stations.clear();
        this.stations.addAll(stations);
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
