package me.gmur.buddywatch.vote.adapter.rest

import me.gmur.buddywatch.vote.domain.app.NumberOfCastVotesExceeded
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class VoteEndpointsAdvice {

    @ExceptionHandler(NumberOfCastVotesExceeded::class)
    fun handleBadRequest(e: NumberOfCastVotesExceeded): ResponseEntity<Any> {
        return ResponseEntity(e.message, HttpStatus.PRECONDITION_FAILED)
    }
}
