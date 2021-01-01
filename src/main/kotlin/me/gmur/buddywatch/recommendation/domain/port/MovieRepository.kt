package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.recommendation.domain.model.Movie

interface MovieRepository {

    fun store(movies: List<Movie>)
}
