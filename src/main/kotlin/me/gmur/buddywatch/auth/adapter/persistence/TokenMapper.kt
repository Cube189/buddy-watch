package me.gmur.buddywatch.auth.adapter.persistence

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.commons.Mapper
import me.gmur.buddywatch.jooq.tables.records.JTokenRecord
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TokenMapper : Mapper<Token, JTokenRecord> {

    override fun mapToEntity(source: Token): JTokenRecord = mapToEntity(source, JTokenRecord())

    override fun mapToEntity(source: Token, base: JTokenRecord): JTokenRecord {
        return base.apply {
            token = source.toString()
        }
    }

    override fun mapToDomain(source: JTokenRecord): Token {
        return Token(UUID.fromString(source.token))
    }
}
