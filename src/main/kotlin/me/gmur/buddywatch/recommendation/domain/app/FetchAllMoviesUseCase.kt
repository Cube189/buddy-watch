package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.provider.domain.port.ProviderRepository
import me.gmur.buddywatch.recommendation.domain.port.MovieClient
import me.gmur.buddywatch.recommendation.domain.port.MovieRepository
import org.springframework.stereotype.Service

@Service
class FetchAllMoviesUseCase(
    private val movieRepository: MovieRepository,
    private val providerRepository: ProviderRepository,
    private val movieClient: MovieClient,
) {

    fun execute() {
        val decades = (1940..2020 step 10)
        val providers = providerRepository.all()

        val movies = movieClient.allFor(decades.toList(), providers)

        movieRepository.store(movies)
    }
}
