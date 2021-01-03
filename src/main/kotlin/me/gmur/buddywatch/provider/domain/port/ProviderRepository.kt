package me.gmur.buddywatch.provider.domain.port

import me.gmur.buddywatch.provider.domain.model.Provider

interface ProviderRepository {

    fun store(providers: List<Provider>)

    fun get(shorthand: Set<String>): Set<Provider>
}
