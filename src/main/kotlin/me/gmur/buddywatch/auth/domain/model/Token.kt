package me.gmur.buddywatch.auth.domain.model

import java.util.UUID
import java.util.UUID.randomUUID

data class Token(
    val value: UUID = randomUUID(),
    val id: Long? = null
) {

    override fun toString(): String {
        return value.toString()
    }
}
