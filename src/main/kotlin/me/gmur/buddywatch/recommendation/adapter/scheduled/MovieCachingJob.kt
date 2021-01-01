package me.gmur.buddywatch.recommendation.adapter.scheduled

import me.gmur.buddywatch.recommendation.domain.app.FetchAllMoviesUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MovieCachingJob(private val fetchAllMoviesUseCase: FetchAllMoviesUseCase) {

    @Scheduled(cron = "0 0 6 * * FRI")
    fun execute() {
        fetchAllMoviesUseCase.execute()
    }
}
