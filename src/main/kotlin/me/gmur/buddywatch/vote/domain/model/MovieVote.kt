package me.gmur.buddywatch.vote.domain.model

import me.gmur.buddywatch.recommendation.domain.model.Movie

data class MovieVote(
    val movie: Movie,
    val voteCount: Int,
)
