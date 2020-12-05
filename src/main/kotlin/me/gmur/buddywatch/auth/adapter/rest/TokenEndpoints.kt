package me.gmur.buddywatch.auth.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.model.TokenId
import me.gmur.buddywatch.common.domain.app.ClientInputException
import me.gmur.buddywatch.group.domain.port.TokenRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/tokens")
class TokenEndpoints(private val repository: TokenRepository) {

    @PostMapping
    fun register(): ResponseEntity<UUID> {
        val new = Token()

        val stored = repository.store(new)

        return ResponseEntity(stored.id.value, HttpStatus.CREATED)
    }

    @GetMapping("/{tokenId}")
    fun validate(@PathVariable tokenId: UUID) {
        val token = Token(tokenId)

        val isValid = repository.exists(token)

        if (!isValid) throw NoSuchTokenException()
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
private class NoSuchTokenException : ClientInputException()
