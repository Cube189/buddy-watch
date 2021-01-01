package me.gmur.buddywatch.recommendation.domain.model

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id

class Movie(
    val title: String,
    val description: String,
    val released: Int,
    val cast: List<CastMember>,
    val genreIds: Set<Int>,
    val reference: Long,
    override val id: MovieId = MovieId.New
) : Entity<MovieId>()

sealed class MovieId : Id<Long>() {
    object New : MovieId()
    data class Persisted(override val value: Long) : MovieId()
}
