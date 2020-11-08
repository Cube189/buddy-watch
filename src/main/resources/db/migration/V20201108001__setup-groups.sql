CREATE TABLE "group"
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(30),
    member_count     INTEGER DEFAULT 2,
    votes_per_member INTEGER DEFAULT 3,
    url              VARCHAR(14)
);

ALTER TABLE token
    ADD COLUMN group_id BIGINT REFERENCES "group" (id);

CREATE TABLE provider
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(30),
    shorthand VARCHAR(10),
    group_id  BIGINT REFERENCES "group" (id)
);
