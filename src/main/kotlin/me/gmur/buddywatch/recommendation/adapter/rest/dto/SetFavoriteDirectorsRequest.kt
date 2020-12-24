package me.gmur.buddywatch.recommendation.adapter.rest.dto

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.justwatch.api.JwRegion
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteDirectorsCommand

data class SetFavoriteDirectorsRequest(val directors: Set<Director>, val region: JwRegion) {

    fun toCommand(token: Token): SetFavoriteDirectorsCommand {
        return SetFavoriteDirectorsCommand(directors, token)
    }
}
