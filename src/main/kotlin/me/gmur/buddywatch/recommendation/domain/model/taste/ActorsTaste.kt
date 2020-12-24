package me.gmur.buddywatch.recommendation.domain.model.taste

class ActorsTaste(val actors: Set<Actor>)

data class Actor(val name: String, val reference: Long)
