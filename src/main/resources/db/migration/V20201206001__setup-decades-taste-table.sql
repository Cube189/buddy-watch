CREATE TABLE decades_taste (
    id BIGSERIAL PRIMARY KEY,
    decades VARCHAR(4)[],
    token_id UUID REFERENCES token(id)
);
