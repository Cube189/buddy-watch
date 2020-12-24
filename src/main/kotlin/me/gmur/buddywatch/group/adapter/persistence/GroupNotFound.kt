package me.gmur.buddywatch.group.adapter.persistence

import me.gmur.buddywatch.auth.domain.model.Token

class GroupNotFound(groupId: Long, token: Token) :
    RuntimeException("Group [$groupId] not found for token [${token.id}]. Is the token assigned?")
