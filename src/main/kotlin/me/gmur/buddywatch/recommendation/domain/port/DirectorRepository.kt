package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.provider.domain.model.Provider
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste

interface DirectorRepository {

    fun fetchFor(decadesTaste: DecadesTaste, genresTaste: GenresTaste, providers: Set<Provider>): Set<Director>
}
