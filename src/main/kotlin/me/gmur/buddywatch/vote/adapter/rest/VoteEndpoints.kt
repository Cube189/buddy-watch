package me.gmur.buddywatch.vote.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.common.adapter.rest.Header.X_TOKEN
import me.gmur.buddywatch.recommendation.domain.model.MovieId
import me.gmur.buddywatch.vote.adapter.rest.dto.VoteRequest
import me.gmur.buddywatch.vote.domain.app.CastVoteUseCase
import me.gmur.buddywatch.vote.domain.app.RankingFactory
import me.gmur.buddywatch.vote.domain.model.Ranking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/votes")
class VoteEndpoints(
    private val rankingFactory: RankingFactory,
    private val castVoteUseCase: CastVoteUseCase,
) {

    @PostMapping("/{movieId}")
    fun cast(
        @RequestHeader(X_TOKEN) tokenId: UUID,
        @PathVariable movieId: Long,
        @RequestBody request: VoteRequest
    ): Int {
        val token = Token(tokenId)
        val movieId = MovieId.Persisted(movieId)

        val command = request.toCommand(token, movieId)

        return castVoteUseCase.execute(command)
    }

    @GetMapping
    fun all(@RequestHeader(X_TOKEN) tokenId: UUID): Ranking {
        val token = Token(tokenId)

        return rankingFactory.create(token)
    }
}
