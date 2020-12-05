package me.gmur.buddywatch.group.adapter.rest.dto

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.model.command.AssignMemberCommand

data class AssignMemberRequest(val groupUrl: String) {

    fun toCommand(memberToken: Token): AssignMemberCommand {
        return AssignMemberCommand(
            GroupUrl(groupUrl),
            memberToken
        )
    }
}
