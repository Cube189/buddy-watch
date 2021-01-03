package me.gmur.buddywatch.group.adapter.rest.dto

import me.gmur.buddywatch.group.domain.model.Group

data class GroupResponse(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providerShortnames: Set<String>,
    val url: String,
) {

    constructor(group: Group) : this(
        group.name,
        group.memberCount,
        group.votesPerMember,
        group.providerShortnames,
        group.url.value
    )
}
