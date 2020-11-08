package me.gmur.buddywatch.group.domain.model

class Group(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providers: Set<Provider>,
    val url: GroupUrl? = null,
    val id: Long? = null
)
