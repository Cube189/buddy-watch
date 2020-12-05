package me.gmur.buddywatch.group.domain.app

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.command.CreateGroupCommand
import me.gmur.buddywatch.group.domain.port.GroupRepository
import org.springframework.stereotype.Service

@Service
class CreateGroupUseCase(
    private val groupRepository: GroupRepository,
) {

    fun execute(command: CreateGroupCommand): Group {
        val group = command.toGroup()

        return groupRepository.store(group)
    }
}
