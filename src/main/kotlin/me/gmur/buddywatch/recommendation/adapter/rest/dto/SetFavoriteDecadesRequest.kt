package me.gmur.buddywatch.recommendation.adapter.rest.dto

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteDecadesCommand

data class SetFavoriteDecadesRequest(val decades: Set<String>) {

    fun toCommand(token: Token): SetFavoriteDecadesCommand {
        return SetFavoriteDecadesCommand(decades, token)
    }
}
