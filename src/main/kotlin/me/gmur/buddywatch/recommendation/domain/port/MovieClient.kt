package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.provider.domain.model.Provider
import me.gmur.buddywatch.recommendation.domain.model.FetchedMovie

interface MovieClient {

    fun allFor(decades: List<Int>, providers: Set<Provider>): List<FetchedMovie>
}
