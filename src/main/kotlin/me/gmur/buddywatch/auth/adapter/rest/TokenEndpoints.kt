package me.gmur.buddywatch.auth.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.model.TokenId
import me.gmur.buddywatch.group.domain.port.TokenRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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

    @GetMapping
    fun validate(@RequestBody tokenId: UUID): ResponseEntity<Any> {
        val token = Token(TokenId.Persisted(tokenId))

        val isValid = repository.exists(token)

        return ResponseEntity(if (isValid) HttpStatus.OK else HttpStatus.NOT_FOUND)
    }
}
