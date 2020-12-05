package me.gmur.buddywatch.group.adapter.rest

import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.group.domain.port.ProvidersRepository
import me.gmur.buddywatch.justwatch.api.Region
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/providers")
class ProviderEndpoints(private val providersRepository: ProvidersRepository) {

    @GetMapping
    fun forRegion(region: String): Set<Provider> {
        // TODO: Map the passed-in region to an actual region
        val region = Region("en_US", "US", "United States")

        return providersRepository.allFor(region)
    }
}
