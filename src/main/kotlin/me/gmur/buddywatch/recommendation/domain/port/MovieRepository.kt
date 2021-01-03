package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.recommendation.domain.model.FetchedMovie
import me.gmur.buddywatch.recommendation.domain.model.Movie
import java.time.LocalDateTime

interface MovieRepository {

    fun store(fetchedMovies: List<FetchedMovie>, timestamp: LocalDateTime)
}
