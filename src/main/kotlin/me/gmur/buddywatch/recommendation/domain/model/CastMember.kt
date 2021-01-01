package me.gmur.buddywatch.recommendation.domain.model

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id

class CastMember(
    val name: String,
    val role: String, // TODO Replace with an enum
    val reference: Long,
    override val id: CastMemberId = CastMemberId.New
) : Entity<CastMemberId>()

sealed class CastMemberId : Id<Long>() {
    object New: CastMemberId()
    data class Persisted(override val value: Long): CastMemberId()
}
