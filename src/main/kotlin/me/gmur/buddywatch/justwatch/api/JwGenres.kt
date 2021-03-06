package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION
import java.time.Duration
import java.time.Instant

class JwGenres(private val context: Context) {

    private val cacheLifetime by lazy {
        val env = System.getenv("CACHE_LIFETIME")

        if (env.isNullOrBlank()) 604800L // 7d
        else env.toLong()
    }
    private lateinit var lastFetched: Instant
    private var cache: Map<Int, JwGenre> = mapOf()
        get() {
            return if (isCacheValid()) field else {
                val results = fetch()
                populateCache(results)

                return field
            }
        }

    private fun isCacheValid(): Boolean {
        if (!::lastFetched.isInitialized) return false

        val now = Instant.now()
        val difference = Duration.between(lastFetched, now).seconds

        return difference < cacheLifetime
    }

    private fun fetch(): Set<JwGenre> {
        val request = Http().path("genres/locale/${context[REGION].first()}")
        val type = object : TypeToken<Set<JwGenre>>() {}

        return request.execute(type)
    }

    private fun populateCache(genres: Set<JwGenre>) {
        lastFetched = Instant.now()

        cache = genres.map { it.id to it }.toMap()
    }

    fun all(): Set<JwGenre> {
        return cache.values.toSet()
    }

    fun get(id: Int): JwGenre? {
        return cache[id]
    }
}
