package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.recommendation.domain.model.Movie
import java.time.LocalDateTime

interface MovieRepository {

    fun store(movies: List<Movie>, timestamp: LocalDateTime)
}
