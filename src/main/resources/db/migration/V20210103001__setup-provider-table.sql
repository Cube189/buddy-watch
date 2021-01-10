CREATE TABLE provider
(
    shorthand  VARCHAR(5) PRIMARY KEY,
    name       TEXT,
    fetched_on TIMESTAMP
);

CREATE VIEW last_provider_cache_fetch_timestamp AS
SELECT fetched_on
FROM provider
ORDER BY fetched_on DESC
WITH CASCADED CHECK OPTION;
