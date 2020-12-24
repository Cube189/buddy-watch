package me.gmur.buddywatch.recommendation.domain.model.taste.command

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste

class SetFavoriteGenresCommand(val genres: Set<Genre>, val token: Token) {

    fun toGenresTaste(): GenresTaste {
        return GenresTaste(genres)
    }
}
