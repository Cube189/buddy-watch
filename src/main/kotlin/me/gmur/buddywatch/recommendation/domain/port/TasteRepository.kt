package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.recommendation.domain.model.taste.ActorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DirectorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste

interface TasteRepository {

    fun store(actors: ActorsTaste, token: Token)

    fun getActors(token: Token): ActorsTaste

    fun store(decades: DecadesTaste, token: Token)

    fun getDecades(token: Token): DecadesTaste

    fun store(directors: DirectorsTaste, token: Token)

    fun getDirectors(token: Token): DirectorsTaste

    fun store(genres: GenresTaste, token: Token)

    fun getGenres(token: Token): GenresTaste
}
