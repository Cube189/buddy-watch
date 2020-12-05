package me.gmur.buddywatch.group.adapter.external

import me.gmur.buddywatch.group.domain.port.ProvidersRepository
import me.gmur.buddywatch.justwatch.api.Provider
import me.gmur.buddywatch.justwatch.api.Region
import org.springframework.stereotype.Repository

@Repository
class JustWatchProvidersRepository : ProvidersRepository {

    override fun allFor(region: Region): Set<Provider> {
        return region.providers().available()
    }
}
