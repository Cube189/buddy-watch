package me.gmur.buddywatch.group.adapter.external

import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.group.domain.port.ProvidersRepository
import me.gmur.buddywatch.justwatch.api.JwProvider
import me.gmur.buddywatch.justwatch.api.JwRegion
import org.springframework.stereotype.Repository
import me.gmur.buddywatch.group.adapter.external.ProviderMapper as mapper

@Repository
class JustWatchProvidersRepository : ProvidersRepository {

    override fun allFor(region: JwRegion): Set<Provider> {
        val providers = region.providers().available()

        return providers.map { mapper.mapToDomain(it) }.toSet()
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
