package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.recommendation.domain.port.MovieClient
import me.gmur.buddywatch.recommendation.domain.port.MovieRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FetchAllMoviesUseCase(
    private val movieRepository: MovieRepository,
    private val movieClient: MovieClient,
) {

    fun execute() {
        val timestamp = LocalDateTime.now()
        val decades = (1940..2020 step 10)

        val movies = movieClient.allFor(decades.toList())

        movieRepository.store(movies, timestamp)
    }
}
