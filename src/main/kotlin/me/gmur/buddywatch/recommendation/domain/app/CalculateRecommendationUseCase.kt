package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.recommendation.domain.model.MovieScore
import me.gmur.buddywatch.recommendation.domain.port.MovieRepository
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service

@Service
class CalculateRecommendationUseCase(
    private val movieRepository: MovieRepository,
    private val tasteRepository: TasteRepository,
    private val groupRepository: GroupRepository,
) {

    fun execute(token: Token): List<MovieScore> {
        val group = groupRepository.ofMember(token)
        val taste = tasteRepository.of(token)

        val movies = movieRepository.all(group.providerShortnames)

        val scored = movies.map { it.score(taste) }
        val sorted = scored.sortedByDescending { it.score }

        return sorted.take(100)
    }
}
