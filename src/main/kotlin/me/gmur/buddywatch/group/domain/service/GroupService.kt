package me.gmur.buddywatch.group.domain.service

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.port.TokenRepository
import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.port.GroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

@Service
@Transactional
class GroupService(
    private val groups: GroupRepository,
    private val tokens: TokenRepository
) {

    fun create(group: Group): Group {
        return groups.store(group)
    }

    fun assign(token: Token, group: Group) {
        Assert.isTrue(groups.exists(group), "A member can only be added to a preexisting group.")
        Assert.isTrue(tokens.exists(token), "Only a preexisting member token can be assigned to a group.")

        val assigned = token.assignTo(group)

        tokens.store(assigned)
    }
}
