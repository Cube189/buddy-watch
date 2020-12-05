package me.gmur.buddywatch.group.domain.app

import me.gmur.buddywatch.group.domain.model.command.AssignMemberCommand
import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.group.domain.port.TokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

@Service
@Transactional
class AssignMemberUseCase(
    private val groupRepository: GroupRepository,
    private val tokenRepository: TokenRepository,
) {

    fun execute(command: AssignMemberCommand) {
        Assert.isTrue(
            groupRepository.exists(command.groupUrl),
            "A member can only be added to a preexisting group."
        )
        Assert.isTrue(
            tokenRepository.exists(command.member),
            "Only a preexisting member token can be assigned to a group."
        )

        val group = groupRepository.get(command.groupUrl)
        val member = command.member

        val assigned = member.assignTo(group)

        tokenRepository.store(assigned)
    }
}
