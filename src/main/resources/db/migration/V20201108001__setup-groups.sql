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

CREATE TABLE group_provider
(
    shorthand VARCHAR(5),
    group_id  BIGINT REFERENCES "group" (id),
    CONSTRAINT shorthand_group_pkey PRIMARY KEY (shorthand, group_id)
);
