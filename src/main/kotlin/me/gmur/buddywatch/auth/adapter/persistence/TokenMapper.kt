package me.gmur.buddywatch.auth.adapter.persistence

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.commons.Mapper
import me.gmur.buddywatch.jooq.tables.JToken.TOKEN
import me.gmur.buddywatch.jooq.tables.records.JTokenRecord
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TokenMapper : Mapper<Token, JTokenRecord> {

    override fun mapToEntity(source: Token): JTokenRecord = mapToEntity(source, JTokenRecord())

    override fun mapToEntity(source: Token, base: JTokenRecord): JTokenRecord {
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

    override fun mapToDomain(source: JTokenRecord): Token {
        return Token(UUID.fromString(source.token), source.groupId, source.id)
    }
}
