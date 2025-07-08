insert into role (name)
values
    ('admin'),
    ('user')
;

insert into accident_type (name)
values
    ('Speeding'),
    ('Running a red light'),
    ('Illegal parking'),
    ('Drunk driving'),
    ('Tailgating')
;

insert into rule (name)
values
    ('Article 12.9 (Speeding)'),
    ('Article 12.12 (Running a red light)'),
    ('Article 12.19 (Illegal parking)'),
    ('Article 12.8 (Drunk driving)'),
    ('Article 12.15 (Improper lane usage)')
;

insert into accident (name, text, address, type_id)
values
    ('Intersection collision', 'Two-car accident at a traffic light', '123 Main St', 2),
    ('Highway speeding', 'Driver going 120 km/h in a 60 km/h zone', 'Highway 101, Mile 15', 1),
    ('Parking on the lawn', 'Car parked on a restricted grassy area', '456 Oak Ave', 3),
    ('Pedestrian accident', 'Car hit a pedestrian in a no-crossing zone', '789 Pine Blvd', 4)
;

insert into accident_rule (accident_id, rule_id)
values
    (1, 2),
    (2, 1),
    (3, 3),
    (4, 4),
    (4, 5)
;