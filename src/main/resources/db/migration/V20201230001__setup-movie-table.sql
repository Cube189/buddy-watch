CREATE TABLE movie
(
    id            BIGINT PRIMARY KEY,
    title         TEXT     NOT NULL,
    description   TEXT,
    released      INT      NOT NULL,
    actor_refs    BIGINT[] NOT NULL,
    director_refs BIGINT[] NOT NULL,
    genre_refs    INT[]    NOT NULL,
    fetched_on    TIMESTAMP
);

CREATE TABLE cast_member
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT NOT NULL,
    role       TEXT NOT NULL,
    fetched_on TIMESTAMP
);

CREATE TABLE movie_provider
(
    provider VARCHAR(5),
    movie_id BIGINT,
    CONSTRAINT provider_movie_pkey PRIMARY KEY (provider, movie_id)
);
