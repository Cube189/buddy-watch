package me.gmur.buddywatch.recommendation.adapter.rest.dto

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.justwatch.api.JwRegion
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteGenresCommand

data class SetFavoriteGenresRequest(val genres: Set<Genre>, val region: JwRegion) {

    fun toCommand(token: Token): SetFavoriteGenresCommand {
        return SetFavoriteGenresCommand(genres, token)
    }
}
