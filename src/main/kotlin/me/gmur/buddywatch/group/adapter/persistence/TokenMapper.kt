package me.gmur.buddywatch.group.adapter.persistence

import me.gmur.buddywatch.group.domain.model.Token
import me.gmur.buddywatch.jooq.tables.JToken.TOKEN
import me.gmur.buddywatch.jooq.tables.records.JTokenRecord
import java.util.UUID

object TokenMapper {

    fun mapToRecord(source: Token): JTokenRecord = mapToRecord(source, JTokenRecord())

    fun mapToRecord(source: Token, base: JTokenRecord): JTokenRecord {
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

    fun mapToDomain(source: JTokenRecord): Token {
        return Token(UUID.fromString(source.token), source.groupId, source.id)
    }
}
