package me.gmur.buddywatch.provider.domain.app

import me.gmur.buddywatch.provider.domain.port.ProviderClient
import me.gmur.buddywatch.provider.domain.port.ProviderRepository
import org.springframework.stereotype.Service

@Service
class FetchAllProvidersUseCase(
    private val providerClient: ProviderClient,
    private val providerRepository: ProviderRepository,
) {

    private val allowed = setOf("ahb", "amp", "atp", "dnp", "hbn", "itu", "nfx")

    fun execute() {
        val fetched = providerClient.fetchAll()

        val filtered = fetched.filter { it.shorthand in allowed }

        providerRepository.store(filtered.toList())
    }
}
