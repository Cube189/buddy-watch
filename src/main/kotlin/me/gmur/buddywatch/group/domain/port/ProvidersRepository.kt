package me.gmur.buddywatch.group.domain.port

import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.justwatch.api.JwRegion

interface ProvidersRepository {

    fun allFor(region: JwRegion): Set<Provider>
}
