CREATE TABLE ranges (
    id BIGINT NOT NULL AUTO_INCREMENT,
    range_from INT NOT NULL,
    range_to INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE sensor_types (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE UNIQUE INDEX ix_name_sensor_types
ON sensor_types (name);

CREATE TABLE sensor_units (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE UNIQUE INDEX ix_name_sensor_units
ON sensor_units (name);

CREATE TABLE sensors (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  description VARCHAR(200) NOT NULL,
  model VARCHAR(15) NOT NULL,
  location VARCHAR(40),
  range_id BIGINT NOT NULL,
  sensor_type_id BIGINT NOT NULL,
  sensor_unit_id BIGINT,
  PRIMARY KEY (id)
);

ALTER TABLE sensors
ADD FOREIGN KEY (range_id) REFERENCES ranges(id) ON DELETE CASCADE;

ALTER TABLE sensors
ADD FOREIGN KEY (sensor_type_id) REFERENCES sensor_types(id) ON DELETE CASCADE;

ALTER TABLE sensors
ADD FOREIGN KEY (sensor_unit_id) REFERENCES sensor_units(id) ON DELETE SET NULL;
