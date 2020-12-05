package me.gmur.buddywatch.group.adapter.rest.dto

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.Provider

data class GroupResponse(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providers: Set<Provider>,
    val url: String,
) {

    constructor(group: Group) : this(
        group.name,
        group.memberCount,
        group.votesPerMember,
        group.providers,
        group.url.value
    )
}
