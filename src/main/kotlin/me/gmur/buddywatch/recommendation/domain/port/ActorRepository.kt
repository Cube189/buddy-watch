package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.recommendation.domain.model.taste.Actor
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste

interface ActorRepository {

    fun fetchFor(
        decadesTaste: DecadesTaste,
        genresTaste: GenresTaste,
        group: Group
    ): Set<Actor>
}
