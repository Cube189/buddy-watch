CREATE VIEW last_cache_fetch_timestamp AS
SELECT fetched_on
FROM movie
ORDER BY fetched_on DESC
LIMIT 1;