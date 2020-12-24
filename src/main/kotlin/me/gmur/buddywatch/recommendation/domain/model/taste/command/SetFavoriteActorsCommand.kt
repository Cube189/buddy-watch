package me.gmur.buddywatch.recommendation.domain.model.taste.command

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.recommendation.domain.model.taste.Actor
import me.gmur.buddywatch.recommendation.domain.model.taste.ActorsTaste

class SetFavoriteActorsCommand(val actors: Set<Actor>, val token: Token) {

    fun toActorsTaste(): ActorsTaste {
        return ActorsTaste(actors)
    }
}
