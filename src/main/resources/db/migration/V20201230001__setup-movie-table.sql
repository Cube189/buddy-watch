CREATE TABLE movie
(
    id           BIGSERIAL PRIMARY KEY,
    title        TEXT     NOT NULL,
    description  TEXT,
    released     INT      NOT NULL,
    actor_ids    BIGINT[] NOT NULL,
    director_ids BIGINT[] NOT NULL,
    genre_ids    INT[]    NOT NULL,
    reference    BIGINT   NOT NULL,
    fetched_on   TIMESTAMP
);

CREATE TABLE cast_member
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT   NOT NULL,
    role       TEXT   NOT NULL,
    reference  BIGINT NOT NULL,
    fetched_on TIMESTAMP
)
