package me.gmur.buddywatch.provider.adapter.scheduled

import me.gmur.buddywatch.provider.domain.app.FetchAllProvidersUseCase
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(0)
class ProviderCachingJob(private val fetchAllProvidersUseCase: FetchAllProvidersUseCase) :
    ApplicationListener<ApplicationReadyEvent> {

    fun execute() {
        fetchAllProvidersUseCase.execute()
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val flag = System.getenv("CACHE_ON_STARTUP")
        if (flag == null || flag.isBlank()) return

        execute()
    }
}
