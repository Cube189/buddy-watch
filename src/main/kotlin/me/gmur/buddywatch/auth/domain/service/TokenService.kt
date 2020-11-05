package me.gmur.buddywatch.auth.domain.service

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.port.TokenRepository
import org.springframework.stereotype.Service

@Service
class TokenService(private val repository: TokenRepository) {

    fun register(): Token {
        val generated = Token()

        repository.store(generated)

        return generated
    }

    fun isValid(token: Token): Boolean {
        return repository.exists(token)
    }
}
