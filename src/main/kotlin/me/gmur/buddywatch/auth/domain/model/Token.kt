package me.gmur.buddywatch.auth.domain.model

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id
import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupId
import java.util.UUID

class Token(
    override val id: TokenId = TokenId.New,
    val group: GroupId? = null
) : Entity<TokenId>() {

    fun assignTo(group: Group): Token {
        return Token(id, group.id)
    }

    override fun toString(): String {
        return id.toString()
    }
}

sealed class TokenId : Id<UUID>() {
    object New : TokenId()
    data class Persisted(override val value: UUID) : TokenId()
}
