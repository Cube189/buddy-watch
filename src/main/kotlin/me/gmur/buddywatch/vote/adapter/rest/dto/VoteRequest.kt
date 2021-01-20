package me.gmur.buddywatch.vote.adapter.rest.dto

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.recommendation.domain.model.MovieId
import me.gmur.buddywatch.vote.domain.model.command.CastVoteCommand

data class VoteRequest(
    val liked: Boolean
) {

    fun toCommand(token: Token, movieId: MovieId): CastVoteCommand {
        return CastVoteCommand(token, movieId, liked)
    }
}
