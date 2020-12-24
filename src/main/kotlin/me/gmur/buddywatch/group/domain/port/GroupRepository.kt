package me.gmur.buddywatch.group.domain.port

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupUrl

interface GroupRepository {

    fun store(group: Group): Group

    fun get(groupUrl: GroupUrl): Group

    fun ofMember(token: Token): Group

    fun exists(group: Group): Boolean

    fun exists(url: GroupUrl): Boolean
}
