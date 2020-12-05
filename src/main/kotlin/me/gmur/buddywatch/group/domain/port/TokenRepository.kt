package me.gmur.buddywatch.group.domain.port

import me.gmur.buddywatch.group.domain.model.Token

interface TokenRepository {

    fun store(token: Token): Token

    fun exists(token: Token): Boolean
}
