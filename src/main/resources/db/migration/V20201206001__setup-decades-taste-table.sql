CREATE TABLE decades_taste
(
    id       BIGSERIAL PRIMARY KEY,
    decades  INT[],
    token_id UUID REFERENCES token (id) UNIQUE
);
