package me.gmur.buddywatch.vote.domain.port

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.GroupId
import me.gmur.buddywatch.recommendation.domain.model.MovieId
import me.gmur.buddywatch.vote.domain.model.Vote

interface VoteRepository {

    fun store(vote: Vote)

    fun allFor(groupId: GroupId): List<Vote>

    fun allFor(token: Token): List<Vote>
}
