package me.gmur.buddywatch.provider.domain.port

import me.gmur.buddywatch.provider.domain.model.Provider

interface ProviderClient {

    fun fetchAll(): List<Provider>
}
