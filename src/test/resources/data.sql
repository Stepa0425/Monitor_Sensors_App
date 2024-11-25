INSERT INTO ranges (range_from, range_to)
VALUES (20, 50),
        (12,19),
        (15,20),
        (22, 45),
        (20, 10);

INSERT INTO sensor_types(name)
VALUES  ('Pressure'),
        ('Voltage'),
        ('Temperature'),
        ('Humidity');

INSERT INTO sensor_units(name)
VALUES  ('bar'),
        ('voltage'),
        ('°С'),
        ('%');

INSERT INTO sensors (
    name,
    model,
    description,
    location,
    range_id,
    sensor_type_id,
    sensor_unit_id
)
SELECT
    'Barometer',
    'ac-23',
    'a device for measuring atmospheric pressure',
    'kitchen',
    (SELECT r.id FROM ranges AS r WHERE r.range_from = 22 AND r.range_to = 45),
    (SELECT t.id FROM sensor_types AS t WHERE t.name = 'Pressure'),
    (SELECT u.id FROM sensor_units AS u WHERE u.name = 'bar');