package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.recommendation.domain.model.FetchedMovie

interface MovieClient {

    fun allFor(decades: Collection<Int>): List<FetchedMovie>
}
