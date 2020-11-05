package me.gmur.buddywatch.auth.domain.model

import java.util.UUID
import java.util.UUID.randomUUID

data class Token(private val value: UUID = randomUUID()) {

    override fun toString(): String {
        return value.toString()
    }
}
