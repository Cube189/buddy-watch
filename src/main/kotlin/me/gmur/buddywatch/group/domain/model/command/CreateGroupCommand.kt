package me.gmur.buddywatch.group.domain.model.command

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.model.Provider

class CreateGroupCommand(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providers: Set<Provider>
) {

    fun toGroup(): Group {
        return Group(name, memberCount, votesPerMember, providers, GroupUrl())
    }
}
