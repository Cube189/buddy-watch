package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.recommendation.domain.model.taste.ActorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DirectorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste

interface TasteRepository {

    fun store(actors: ActorsTaste, token: Token): ActorsTaste

    fun getActors(token: Token): ActorsTaste

    fun store(decades: DecadesTaste, token: Token): DecadesTaste

    fun getDecades(token: Token): DecadesTaste

    fun store(directors: DirectorsTaste, token: Token): DirectorsTaste

    fun getDirectors(token: Token): DirectorsTaste

    fun store(genres: GenresTaste, token: Token): GenresTaste

    fun getGenres(token: Token): GenresTaste
}
