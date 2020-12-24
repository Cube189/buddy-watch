package me.gmur.buddywatch.recommendation.adapter.rest.dto

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.justwatch.api.JwRegion
import me.gmur.buddywatch.recommendation.domain.model.taste.Actor
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteActorsCommand

data class SetFavoriteActorsRequest(val actors: Set<Actor>, val region: JwRegion) {

    fun toCommand(token: Token): SetFavoriteActorsCommand {
        return SetFavoriteActorsCommand(actors, token)
    }
}
