package me.gmur.buddywatch.group.domain.model

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id

class Group(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providers: Set<Provider>,
    val url: GroupUrl = GroupUrl(),
    override val id: GroupId = GroupId.New
) : Entity<GroupId>()

sealed class GroupId : Id<Long>() {
    object New : GroupId()
    data class Persisted(override val value: Long) : GroupId()
}
