package me.gmur.buddywatch.auth.domain.model

import me.gmur.buddywatch.group.domain.model.Group
import java.util.UUID
import java.util.UUID.randomUUID

data class Token(
    val value: UUID = randomUUID(),
    val group: Long? = null,
    val id: Long? = null
) {

    fun assignTo(group: Group): Token {
        return Token(value, group.id, id)
    }

    override fun toString(): String {
        return value.toString()
    }
}
