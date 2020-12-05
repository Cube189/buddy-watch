package me.gmur.buddywatch.group.adapter.rest.dto

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.model.TokenId
import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.model.command.AssignMemberCommand
import java.util.UUID

data class AssignMemberRequest(
    val groupUrl: String,
    val memberToken: UUID
) {

    fun toCommand(): AssignMemberCommand {
        return AssignMemberCommand(
            GroupUrl(groupUrl),
            Token(TokenId.Persisted(memberToken))
        )
    }
}
