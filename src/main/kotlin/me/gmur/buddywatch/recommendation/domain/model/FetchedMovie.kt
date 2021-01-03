package me.gmur.buddywatch.recommendation.domain.model

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id

class FetchedMovie(
    val title: String,
    val description: String,
    val released: Int,
    val cast: List<CastMember>,
    val genreReferences: Set<Int>,
    val reference: Long,
    override val id: FetchedMovieId = FetchedMovieId.New
) : Entity<FetchedMovieId>()

sealed class FetchedMovieId : Id<Long>() {
    object New : FetchedMovieId()
    data class Persisted(override val value: Long) : FetchedMovieId()
}
