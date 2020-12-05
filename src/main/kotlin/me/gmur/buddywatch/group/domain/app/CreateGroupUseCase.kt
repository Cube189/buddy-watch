package me.gmur.buddywatch.group.domain.app

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.command.CreateGroupCommand
import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.group.domain.port.TokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

@Service
@Transactional
class CreateGroupUseCase(
    private val groupRepository: GroupRepository,
    private val tokenRepository: TokenRepository,
) {

    fun execute(command: CreateGroupCommand): Group {
        Assert.isTrue(
            tokenRepository.exists(command.owner),
            "Could not find a user set as the group's owner."
        )

        val owner = command.owner
        val group = command.toGroup()

        val stored = groupRepository.store(group)
        assignOwnerToGroup(owner, stored)

        return stored
    }

    private fun assignOwnerToGroup(owner: Token, group: Group) {
        val assigned = owner.assignTo(group)

        tokenRepository.store(assigned)
    }
}
