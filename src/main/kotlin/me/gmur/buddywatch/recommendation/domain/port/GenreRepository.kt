package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.justwatch.api.JwRegion
import me.gmur.buddywatch.provider.domain.model.Provider
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre

interface GenreRepository {

    fun fetchFor(decadeTaste: DecadesTaste, providers: Set<Provider>, region: JwRegion): Set<Genre>
}
