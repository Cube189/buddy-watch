package me.gmur.buddywatch.group.adapter.rest.dto

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.group.domain.model.command.CreateGroupCommand

data class CreateGroupRequest(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providers: Set<Provider>
) {

    fun toCommand(owner: Token): CreateGroupCommand {
        return CreateGroupCommand(name, memberCount, votesPerMember, providers, owner)
    }
}
