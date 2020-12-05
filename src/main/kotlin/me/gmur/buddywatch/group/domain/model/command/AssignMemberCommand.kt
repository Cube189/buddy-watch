package me.gmur.buddywatch.group.domain.model.command

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.GroupUrl

class AssignMemberCommand(
    val groupUrl: GroupUrl,
    val member: Token
)
