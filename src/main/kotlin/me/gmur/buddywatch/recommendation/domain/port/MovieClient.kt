package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.recommendation.domain.model.Movie

interface MovieClient {

    fun allFor(decades: Collection<Int>): List<Movie>
}
