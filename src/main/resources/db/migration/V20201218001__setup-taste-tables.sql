CREATE TABLE directors_taste
(
    id       BIGSERIAL PRIMARY KEY,
    names    TEXT[],
    token_id UUID REFERENCES token (id)
);

CREATE TABLE genres_taste
(
    id       BIGSERIAL PRIMARY KEY,
    values   TEXT[],
    token_id UUID REFERENCES token (id)
);

CREATE TABLE actors_taste
(
    id       BIGSERIAL PRIMARY KEY,
    names   TEXT[],
    token_id UUID REFERENCES token (id)
);
