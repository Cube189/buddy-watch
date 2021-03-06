package me.gmur.buddywatch.recommendation.adapter.scheduled

import me.gmur.buddywatch.recommendation.domain.app.FetchAllMoviesUseCase
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.annotation.Order
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@Order(1)
class MovieCachingJob(private val fetchAllMoviesUseCase: FetchAllMoviesUseCase) :
    ApplicationListener<ApplicationReadyEvent> {

    fun execute() {
        fetchAllMoviesUseCase.execute()
    }

    @Scheduled(cron = "0 0 6 * * FRI")
    fun scheduled() {
        execute()
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val flag = System.getenv("CACHE_ON_STARTUP")
        if (flag == null || flag.isBlank()) return

        execute()
    }
}
