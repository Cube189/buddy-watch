package me.gmur.buddywatch.recommendation.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.common.adapter.rest.Header.X_TOKEN
import me.gmur.buddywatch.recommendation.adapter.rest.dto.SetFavoriteActorsRequest
import me.gmur.buddywatch.recommendation.adapter.rest.dto.SetFavoriteDecadesRequest
import me.gmur.buddywatch.recommendation.adapter.rest.dto.SetFavoriteDirectorsRequest
import me.gmur.buddywatch.recommendation.adapter.rest.dto.SetFavoriteGenresRequest
import me.gmur.buddywatch.recommendation.domain.app.SetFavoriteActorsUseCase
import me.gmur.buddywatch.recommendation.domain.app.SetFavoriteDecadesUseCase
import me.gmur.buddywatch.recommendation.domain.app.SetFavoriteDirectorsUseCase
import me.gmur.buddywatch.recommendation.domain.app.SetFavoriteGenresUseCase
import me.gmur.buddywatch.recommendation.domain.model.taste.Actor
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/tastes")
class TasteEndpoints(
    private val setFavoriteDecadesUseCase: SetFavoriteDecadesUseCase,
    private val setFavoriteGenresUseCase: SetFavoriteGenresUseCase,
    private val setFavoriteDirectorsUseCase: SetFavoriteDirectorsUseCase,
    private val setFavoriteActorsUseCase: SetFavoriteActorsUseCase,
) {

    @PostMapping("/decades")
    fun favoriteDecades(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @RequestBody request: SetFavoriteDecadesRequest
    ): Set<Genre> {
        val token = Token(tokenId)
        val command = request.toCommand(token)

        return setFavoriteDecadesUseCase.execute(command)
    }

    @PostMapping("/genres")
    fun favoriteGenres(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @RequestBody request: SetFavoriteGenresRequest
    ): Set<Director> {
        val token = Token(tokenId)
        val command = request.toCommand(token)

        return setFavoriteGenresUseCase.execute(command)
    }

    @PostMapping("/directors")
    fun favoriteDirectors(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @RequestBody request: SetFavoriteDirectorsRequest
    ): Set<Actor> {
        val token = Token(tokenId)
        val command = request.toCommand(token)

        return setFavoriteDirectorsUseCase.execute(command)
    }

    @PostMapping("/actors")
    @ResponseStatus(NO_CONTENT)
    fun favoriteActors(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @RequestBody request: SetFavoriteActorsRequest
    ) {
        val token = Token(tokenId)
        val command = request.toCommand(token)

        return setFavoriteActorsUseCase.execute(command)
    }
}
