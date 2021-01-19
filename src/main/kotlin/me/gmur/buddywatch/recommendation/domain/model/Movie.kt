package me.gmur.buddywatch.recommendation.domain.model

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id
import me.gmur.buddywatch.recommendation.domain.model.taste.ActorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DirectorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Taste

class Movie(
    val title: String,
    val description: String,
    val released: Int,
    val actorReferences: List<CastMemberId>,
    val directorReferences: List<CastMemberId>,
    val genreReferences: Set<Int>,
    val reference: Long,
    override val id: MovieId = MovieId.New
) : Entity<MovieId>() {

    fun score(taste: Taste): MovieScore {
        val score = calculateScore(taste)

        return MovieScore(this, score)
    }

    private fun calculateScore(taste: Taste): Int {
        val decadesScore = isSetInAnyDecade(taste.decades).toInt() * 5
        val genreScore = isOfGenre(taste.genres).toInt() * 5
        val actorsScore = hasNumberOfActors(taste.actors) * 3
        val directorsScore = hasNumberOfDirectors(taste.directors) * 3

        return decadesScore + genreScore + actorsScore + directorsScore
    }

    private fun isSetInAnyDecade(taste: DecadesTaste): Boolean {
        val decades = taste.decades.map { it.toRange() }

        return decades.any { it.contains(released) }
    }

    private fun isOfGenre(taste: GenresTaste): Boolean {
        val genres = taste.genres.map { it.reference }

        return genreReferences.any { genres.contains(it) }
    }

    private fun hasNumberOfActors(taste: ActorsTaste): Int {
        val actors = taste.actors.map { it.reference }

        val unwrappedReferences = actorReferences.map { it.value }

        return unwrappedReferences.count { actors.contains(it) }
    }

    private fun hasNumberOfDirectors(taste: DirectorsTaste): Int {
        val directors = taste.directors.map { it.reference }

        val unwrappedReferences = directorReferences.map { it.value }

        return unwrappedReferences.count { directors.contains(it) }
    }

    private fun Boolean.toInt(): Int = if (this) 1 else 0
}

sealed class MovieId : Id<Long>() {
    object New : MovieId()
    data class Persisted(override val value: Long) : MovieId()
}
