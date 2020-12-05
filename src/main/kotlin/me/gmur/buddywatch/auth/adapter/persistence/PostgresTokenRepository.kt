package me.gmur.buddywatch.auth.adapter.persistence

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.model.TokenId
import me.gmur.buddywatch.group.domain.model.GroupId
import me.gmur.buddywatch.group.domain.port.TokenRepository
import me.gmur.buddywatch.jooq.tables.Token.Companion.TOKEN
import me.gmur.buddywatch.jooq.tables.records.TokenRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID.randomUUID
import me.gmur.buddywatch.auth.adapter.persistence.TokenMapper as mapper

@Repository
class PostgresTokenRepository(private val db: DSLContext) : TokenRepository {

    override fun store(token: Token): Token {
        val record = mapper.mapToRecord(token, db.newRecord(TOKEN))

        record.store()

        return mapper.mapToDomain(record)
    }

    override fun exists(token: Token): Boolean {
        return db.fetchExists(
            db.selectFrom(TOKEN).where(TOKEN.ID.eq(token.id.value))
        )
    }
}

private object TokenMapper {

    fun mapToRecord(source: Token, base: TokenRecord): TokenRecord {
        val mapped = base.apply {
            id = if (source.id == TokenId.New) randomUUID() else source.id.value
            groupId = source.group?.value
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
        val id = TokenId.Persisted(source.id!!)

        return when (source.groupId) {
            null -> Token(id)
            else -> Token(id, GroupId.Persisted(source.groupId!!))
        }
    }
}
