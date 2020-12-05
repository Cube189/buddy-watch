package me.gmur.buddywatch.group.adapter.rest

import me.gmur.buddywatch.group.domain.model.Token
import me.gmur.buddywatch.group.domain.port.TokenRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tokens")
class TokenEndpoints(private val repository: TokenRepository) {

    @PostMapping
    fun register(): ResponseEntity<String> {
        val generated = Token()

        repository.store(generated)

        return ResponseEntity(generated.toString(), HttpStatus.CREATED)
    }

    @GetMapping
    fun validate(@RequestBody token: String): ResponseEntity<Any> {
        val isValid = repository.exists(Token(token))

        return ResponseEntity(if (isValid) HttpStatus.OK else HttpStatus.NOT_FOUND)
    }
}
