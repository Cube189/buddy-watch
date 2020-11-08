package me.gmur.buddywatch.group.domain.model

class Group(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providers: Set<Provider>,
    val url: GroupUrl? = null,
    val id: Long? = null
) {

    constructor(
        name: String,
        memberCount: Int,
        votesPerMember: Int,
        providers: List<Provider>,
        url: GroupUrl? = null,
        id: Long? = null
    ) : this(name, memberCount, votesPerMember, providers.toSet(), url, id)
}
