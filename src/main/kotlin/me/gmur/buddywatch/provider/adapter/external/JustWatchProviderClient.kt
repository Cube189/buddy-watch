package me.gmur.buddywatch.provider.adapter.external

import me.gmur.buddywatch.justwatch.api.JwProvider
import me.gmur.buddywatch.justwatch.api.JwRegion
import me.gmur.buddywatch.provider.domain.model.Provider
import me.gmur.buddywatch.provider.domain.port.ProviderClient
import org.springframework.stereotype.Repository
import me.gmur.buddywatch.provider.adapter.external.ProviderMapper as mapper

@Repository
class JustWatchProviderClient : ProviderClient {

    override fun fetchAll(): List<Provider> {
        val region = JwRegion("en_US", "US", "United States")
        val providers = region.providers().available()

        return providers.map { mapper.mapToDomain(it) }
    }
}

private object ProviderMapper {

    fun mapToDomain(source: JwProvider): Provider {
        return Provider(
            name = source.name,
            shorthand = source.shorthand
        )
    }
}
