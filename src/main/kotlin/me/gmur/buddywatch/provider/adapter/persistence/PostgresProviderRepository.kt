package me.gmur.buddywatch.provider.adapter.persistence

import me.gmur.buddywatch.jooq.tables.records.ProviderRecord
import me.gmur.buddywatch.jooq.tables.references.LAST_MOVIE_CACHE_FETCH_TIMESTAMP
import me.gmur.buddywatch.jooq.tables.references.PROVIDER
import me.gmur.buddywatch.provider.domain.model.Provider
import me.gmur.buddywatch.provider.domain.port.ProviderRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class PostgresProviderRepository(private val db: DSLContext) : ProviderRepository {

    override fun store(providers: List<Provider>) {
        val timestamp = LocalDateTime.now()

        val records = providers.map { ProviderMapper.mapToRecord(it, db.newRecord(PROVIDER)) }
        records.forEach { it.fetchedOn = timestamp }

        db.batchStore(records).execute()
    }

    override fun findAll(shorthand: Set<String>): Set<Provider> {
        val lastCacheTimestamp = lastCacheTimestamp()

        val records = db.selectFrom(PROVIDER)
            .where(
                PROVIDER.FETCHED_ON.eq(lastCacheTimestamp)
                    .and(PROVIDER.SHORTHAND.`in`(shorthand))
            )
            .fetch()

        return records.map { ProviderMapper.mapToDomain(it) }.toSet()
    }

    private fun lastCacheTimestamp(): LocalDateTime {
        return db.selectFrom(LAST_MOVIE_CACHE_FETCH_TIMESTAMP).fetchOne()!!.fetchedOn!!
    }
}

private object ProviderMapper {

    fun mapToRecord(source: Provider, base: ProviderRecord): ProviderRecord {
        return base.apply {
            shorthand = source.shorthand
            name = source.name
        }
    }

    fun mapToDomain(source: ProviderRecord): Provider {
        return Provider(source.shorthand!!, source.name!!)
    }
}
