package me.gmur.buddywatch.provider.adapter.rest

import me.gmur.buddywatch.provider.domain.model.Provider
import me.gmur.buddywatch.provider.domain.port.ProviderRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/providers")
class ProviderEndpoints(private val providerRepository: ProviderRepository) {

    @GetMapping("/{region}")
    fun forRegion(@PathVariable region: String): Set<Provider> {
        // TODO: Map the passed-in region to an actual region

        return providerRepository.all()
    }
}
