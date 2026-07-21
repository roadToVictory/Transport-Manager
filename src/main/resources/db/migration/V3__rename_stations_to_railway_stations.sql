ALTER TABLE stations RENAME TO railway_stations;
ALTER TABLE railway_stations RENAME CONSTRAINT pk_stations TO pk_railway_stations;
ALTER TABLE railway_stations RENAME CONSTRAINT uk_stations_pkp_id TO uk_railway_stations_pkp_id;
ALTER SEQUENCE IF EXISTS stations_id_seq RENAME TO railway_stations_id_seq;
