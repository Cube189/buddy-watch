package me.gmur.buddywatch.group.domain.model.command

import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.model.Token

class AssignMemberCommand(
    val groupUrl: GroupUrl,
    val member: Token
)
