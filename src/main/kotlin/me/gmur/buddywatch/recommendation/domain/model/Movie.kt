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
    val actorIds: List<CastMemberId>,
    val directorIds: List<CastMemberId>,
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
        TODO("Needs JW references in Taste")
    }

    private fun hasNumberOfActors(taste: ActorsTaste): Int {
        TODO("Needs DB ID of actors")
    }

    private fun hasNumberOfDirectors(taste: DirectorsTaste): Int {
        TODO("Needs DB id of directors")
    }

    private fun Boolean.toInt(): Int = if (this) 1 else 0
}

sealed class MovieId : Id<Long>() {
    object New : MovieId()
    data class Persisted(override val value: Long) : MovieId()
}
