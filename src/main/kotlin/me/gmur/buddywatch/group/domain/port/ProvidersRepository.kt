package me.gmur.buddywatch.group.domain.port

import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.justwatch.api.Region

interface ProvidersRepository {

    fun allFor(region: Region): Set<Provider>
}
