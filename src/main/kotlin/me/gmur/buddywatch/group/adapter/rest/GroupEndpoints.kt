package me.gmur.buddywatch.group.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.adapter.rest.dto.AssignMemberRequest
import me.gmur.buddywatch.group.adapter.rest.dto.CreateGroupRequest
import me.gmur.buddywatch.group.adapter.rest.dto.GroupResponse
import me.gmur.buddywatch.group.domain.app.AssignMemberUseCase
import me.gmur.buddywatch.group.domain.app.CreateGroupUseCase
import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.port.GroupRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/groups")
class GroupEndpoints(
    private val createGroupUseCase: CreateGroupUseCase,
    private val assignMemberUseCase: AssignMemberUseCase,
    private val groupRepository: GroupRepository,
) {

    @PostMapping
    fun create(@RequestBody request: CreateGroupRequest): GroupResponse {
        val command = request.toCommand()

        val result = createGroupUseCase.execute(command)

        return GroupResponse(result)
    }

    @PatchMapping
    fun assignMember(@RequestBody request: AssignMemberRequest) {
        val command = request.toCommand()

         assignMemberUseCase.execute(command)
    }

    @GetMapping("/{groupUrl}")
    fun get(@PathVariable url: String): GroupResponse {
        val groupUrl = GroupUrl(url)

        val result = groupRepository.get(groupUrl)

        return GroupResponse(result)
    }
}
