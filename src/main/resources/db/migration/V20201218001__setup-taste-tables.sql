CREATE TABLE directors_taste
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT,
    reference BIGINT,
    token_id  UUID REFERENCES token (id)
);

CREATE TABLE genres_taste
(
    id        BIGSERIAL PRIMARY KEY,
    value     TEXT,
    shorthand VARCHAR(3),
    reference INT,
    token_id  UUID REFERENCES token (id)
);

CREATE TABLE actors_taste
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT,
    reference BIGINT,
    token_id  UUID REFERENCES token (id)
);
