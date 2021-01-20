package me.gmur.buddywatch.vote.domain.model.command

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.recommendation.domain.model.MovieId

class CastVoteCommand(val token: Token, val movieId: MovieId, val liked: Boolean)
