package me.gmur.buddywatch.group.adapter.rest

import me.gmur.buddywatch.group.domain.app.AssignMemberUseCase
import me.gmur.buddywatch.group.domain.app.CreateGroupUseCase
import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.group.domain.model.Token
import me.gmur.buddywatch.group.domain.model.command.AssignMemberCommand
import me.gmur.buddywatch.group.domain.model.command.CreateGroupCommand
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/group")
class GroupEndpoints(
    private val createGroupUseCase: CreateGroupUseCase,
    private val assignMemberUseCase: AssignMemberUseCase,
) {

    @PostMapping
    fun create(@RequestBody request: CreateGroupRequest): Group {
        val command = request.toCommand()

        return createGroupUseCase.execute(command)
    }

    @PatchMapping
    fun assignMember(@RequestBody request: AssignMemberRequest) {
        val command = request.toCommand()

        return assignMemberUseCase.execute(command)
    }
}

data class AssignMemberRequest(
    val groupUrl: String,
    val memberToken: String
) {

    fun toCommand(): AssignMemberCommand {
        return AssignMemberCommand(
            GroupUrl(groupUrl),
            Token(memberToken)
        )
    }
}

data class CreateGroupRequest(
    val name: String,
    val memberCount: Int,
    val votesPerMember: Int,
    val providers: Set<Provider>
) {

    fun toCommand(): CreateGroupCommand {
        return CreateGroupCommand(name, memberCount, votesPerMember, providers)
    }
}
