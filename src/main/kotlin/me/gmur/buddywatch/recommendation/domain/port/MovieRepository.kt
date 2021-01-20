package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.recommendation.domain.model.FetchedMovie
import me.gmur.buddywatch.recommendation.domain.model.Movie
import me.gmur.buddywatch.recommendation.domain.model.MovieId

interface MovieRepository {

    fun store(fetchedMovies: List<FetchedMovie>)

    fun all(providers: Set<String>): List<Movie>

    fun find(id: MovieId): Movie
}
