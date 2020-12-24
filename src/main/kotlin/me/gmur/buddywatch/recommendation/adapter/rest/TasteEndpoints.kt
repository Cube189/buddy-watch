package me.gmur.buddywatch.recommendation.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.common.adapter.rest.Header.X_TOKEN
import me.gmur.buddywatch.recommendation.adapter.rest.dto.SetFavoriteActorsRequest
import me.gmur.buddywatch.recommendation.adapter.rest.dto.SetFavoriteDecadesRequest
import me.gmur.buddywatch.recommendation.adapter.rest.dto.SetFavoriteDirectorsRequest
import me.gmur.buddywatch.recommendation.adapter.rest.dto.SetFavoriteGenresRequest
import me.gmur.buddywatch.recommendation.domain.app.SetFavoriteDecadesUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import me.gmur.buddywatch.justwatch.api.JwGenre

@RestController
@RequestMapping("/tastes")
class TasteEndpoints(
    private val setFavoriteDecadesUseCase: SetFavoriteDecadesUseCase,
) {

    @PostMapping("/decades")
    fun favoriteDecades(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @RequestBody request: SetFavoriteDecadesRequest
    ): Set<JwGenre> {
        val token = Token(tokenId)
        val command = request.toCommand(token)

        return setFavoriteDecadesUseCase.execute(command)
    }

    @PostMapping("/genres")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun favoriteGenres(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @RequestBody request: SetFavoriteGenresRequest
        val token = Token(tokenId)

        TODO()
    }

    @PostMapping("/directors")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun favoriteDirectors(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @RequestBody request: SetFavoriteDirectorsRequest
    ) {
        val token = Token(tokenId)

        TODO()
    }

    @PostMapping("/actors")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun favoriteActors(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @RequestBody request: SetFavoriteActorsRequest
    ) {
        val token = Token(tokenId)

        TODO()
    }
}
