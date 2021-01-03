package me.gmur.buddywatch.provider.domain.app

import me.gmur.buddywatch.provider.domain.port.ProviderClient
import me.gmur.buddywatch.provider.domain.port.ProviderRepository
import org.springframework.stereotype.Service

@Service
class FetchAllProvidersUseCase(
    private val providerClient: ProviderClient,
    private val providerRepository: ProviderRepository,
) {

    fun execute() {
        val fetched = providerClient.fetchAll()

        providerRepository.store(fetched.toList())
    }
}
