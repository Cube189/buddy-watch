CREATE TABLE vote
(
    id                    BIGSERIAL PRIMARY KEY,
    token_id              UUID REFERENCES token (id),
    group_id              BIGINT REFERENCES "group" (id),
    movie_id              BIGINT REFERENCES movie (id),
    cache_fetch_timestamp TIMESTAMP,
    liked                 BOOLEAN
);
