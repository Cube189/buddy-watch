CREATE TABLE movie
(
    id                 BIGSERIAL PRIMARY KEY,
    title              TEXT     NOT NULL,
    description        TEXT,
    released           INT      NOT NULL,
    actor_refs         BIGINT[] NOT NULL,
    director_refs      BIGINT[] NOT NULL,
    genre_refs         INT[]    NOT NULL,
    reference          BIGINT   NOT NULL,
    provider_shorthand VARCHAR(5),
    fetched_on         TIMESTAMP
);

CREATE TABLE cast_member
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT   NOT NULL,
    role       TEXT   NOT NULL,
    reference  BIGINT NOT NULL,
    fetched_on TIMESTAMP
)
