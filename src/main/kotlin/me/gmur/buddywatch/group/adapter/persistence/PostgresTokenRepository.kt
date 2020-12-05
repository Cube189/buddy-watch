package me.gmur.buddywatch.group.adapter.persistence

import me.gmur.buddywatch.group.domain.model.Token
import me.gmur.buddywatch.group.domain.port.TokenRepository
import me.gmur.buddywatch.jooq.tables.JToken.TOKEN
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import me.gmur.buddywatch.group.adapter.persistence.TokenMapper as mapper

@Repository
class PostgresTokenRepository(private val db: DSLContext) : TokenRepository {

    override fun store(token: Token): Token {
        val record = mapper.mapToRecord(token, db.newRecord(TOKEN))

        record.store()

        return mapper.mapToDomain(record)
    }

    override fun exists(token: Token): Boolean {
        return db.fetchExists(
            db.selectFrom(TOKEN).where(TOKEN.TOKEN_.eq(token.toString()))
        )
    }
}
