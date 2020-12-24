CREATE TABLE directors_taste
(
    id       BIGSERIAL PRIMARY KEY,
    name     TEXT,
    token_id UUID REFERENCES token (id) UNIQUE
);

CREATE TABLE genres_taste
(
    id        BIGSERIAL PRIMARY KEY,
    value     TEXT,
    shorthand VARCHAR(3),
    token_id  UUID REFERENCES token (id) UNIQUE
);

CREATE TABLE actors_taste
(
    id       BIGSERIAL PRIMARY KEY,
    name     TEXT,
    token_id UUID REFERENCES token (id) UNIQUE
);
