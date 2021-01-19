package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.recommendation.domain.model.FetchedMovie
import me.gmur.buddywatch.recommendation.domain.model.Movie

interface MovieRepository {

    fun store(fetchedMovies: List<FetchedMovie>)

    fun all(providers: Set<String>): List<Movie>
}
