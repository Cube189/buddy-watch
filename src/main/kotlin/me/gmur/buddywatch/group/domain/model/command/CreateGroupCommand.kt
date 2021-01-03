package me.gmur.buddywatch.group.domain.model.command

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupUrl

class CreateGroupCommand(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providerShortnames: Set<String>,
    val owner: Token
) {

    fun toGroup(): Group {
        return Group(name, memberCount, votesPerMember, providerShortnames, GroupUrl())
    }
}
