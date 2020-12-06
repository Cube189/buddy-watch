package me.gmur.buddywatch.recommendation.domain.model.taste

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id

class ActorsTaste(
    override val id: ActorsTasteId = ActorsTasteId.New
) : Entity<ActorsTasteId>()

sealed class ActorsTasteId : Id<Long>() {
    object New : ActorsTasteId()
    data class Persisted(override val value: Long) : ActorsTasteId()
}