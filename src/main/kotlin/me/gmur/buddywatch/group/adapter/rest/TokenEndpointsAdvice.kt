package me.gmur.buddywatch.group.adapter.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TokenEndpointsAdvice {

    @ExceptionHandler(IllegalArgumentException::class, NumberFormatException::class)
    fun handleBadRequest(): ResponseEntity<Any> {
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }
}
