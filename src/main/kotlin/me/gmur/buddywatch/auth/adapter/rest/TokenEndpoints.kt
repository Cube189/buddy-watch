package me.gmur.buddywatch.auth.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token")
class TokenEndpoints(private val service: TokenService) {

    @PostMapping
    fun register(): ResponseEntity<Token> {
        return ResponseEntity(service.register(), HttpStatus.CREATED)
    }

    @GetMapping
    fun validate(token: Token): ResponseEntity<Any> {
        val isValid = service.isValid(token)

        return ResponseEntity(if (isValid) HttpStatus.OK else HttpStatus.NOT_FOUND)
    }
}
