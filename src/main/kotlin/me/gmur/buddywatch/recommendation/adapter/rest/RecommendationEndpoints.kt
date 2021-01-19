package me.gmur.buddywatch.recommendation.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.common.adapter.rest.Header.X_TOKEN
import me.gmur.buddywatch.recommendation.domain.app.CalculateRecommendationUseCase
import me.gmur.buddywatch.recommendation.domain.model.MovieScore
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/recommendations")
class RecommendationEndpoints(private val calculateRecommendationsUseCase: CalculateRecommendationUseCase) {

    @GetMapping
    fun recommendMovies(@RequestHeader(X_TOKEN) tokenId: UUID): List<MovieScore> {
        val token = Token(tokenId)

        return calculateRecommendationsUseCase.execute(token)
    }
}
