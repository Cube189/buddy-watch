package me.gmur.buddywatch.group.adapter.persistence

import me.gmur.buddywatch.group.domain.model.Token
import me.gmur.buddywatch.group.domain.port.TokenRepository
import me.gmur.buddywatch.jooq.tables.Token.Companion.TOKEN
import me.gmur.buddywatch.jooq.tables.records.TokenRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID
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

private object TokenMapper {

    fun mapToRecord(source: Token, base: TokenRecord): TokenRecord {
        val mapped = base.apply {
            id = source.id
            token = source.toString()
            groupId = source.group
        }

        if (mapped.id == null) {
            mapped.changed(TOKEN.ID, false)
        }

        if (mapped.groupId == null) {
            mapped.changed(TOKEN.GROUP_ID, false)
        }

        return mapped
    }

    fun mapToDomain(source: TokenRecord): Token {
        return Token(UUID.fromString(source.token), source.groupId, source.id)
    }
}
