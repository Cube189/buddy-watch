package me.gmur.buddywatch.group.domain.port

import me.gmur.buddywatch.justwatch.api.Provider
import me.gmur.buddywatch.justwatch.api.Region

interface ProvidersRepository {

    fun allFor(region: Region): Set<Provider> // TODO: Map and return a domain object
}
