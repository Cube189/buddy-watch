package me.gmur.buddywatch.auth.domain.port

import me.gmur.buddywatch.auth.domain.model.Token

interface TokenRepository {

    fun store(token: Token): Token

    fun exists(token: Token): Boolean
}
