package me.gmur.buddywatch.group.domain.service

import me.gmur.buddywatch.group.domain.model.Token
import me.gmur.buddywatch.group.domain.port.TokenRepository
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
