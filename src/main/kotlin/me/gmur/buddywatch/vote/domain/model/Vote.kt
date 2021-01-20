package me.gmur.buddywatch.vote.domain.model

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id
import me.gmur.buddywatch.group.domain.model.GroupId
import me.gmur.buddywatch.recommendation.domain.model.MovieId

class Vote(
    val token: Token,
    val groupId: GroupId,
    val movieId: MovieId,
    val liked: Boolean,
    override val id: VoteId = VoteId.New,
) : Entity<VoteId>()

sealed class VoteId : Id<Long>() {
    object New : VoteId()
    data class Persisted(override val value: Long) : VoteId()
}
