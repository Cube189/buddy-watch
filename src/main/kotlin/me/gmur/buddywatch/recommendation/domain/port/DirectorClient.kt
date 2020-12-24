package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste

interface DirectorClient {

    fun fetchFor(decadesTaste: DecadesTaste, genresTaste: GenresTaste, group: Group): Set<Director>
}
